package info.knigoed.dao;

import info.knigoed.util.UrlGenerator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

/**
 * Search on the basis of the Sphinx engine via SphinxQL.
 *
 * @author mark
 */
@Service
public class SearchSphinxDao {

    private final static Logger LOG = LoggerFactory.getLogger(SearchSphinxDao.class);

    @Autowired
    private Connection sqlSphinx;

    private StringBuilder booksId;
    private final Map<Integer, String> years = new HashMap<Integer, String>();
    private LinkedHashMap<Long, Long> shopsId = new LinkedHashMap<>();
    private final StringBuilder filters = new StringBuilder();
    private int quorum = 3;
    private int rating = 30;
    private int total = 0, totalFound = 0;
    private float time = 0;
    private final UrlGenerator urlGenerator = new UrlGenerator();


    public SearchSphinxDao() {

    }

    public UrlGenerator getUrlGenerator() {
        return urlGenerator;
    }

    /**
     * @param quorum Любые 40% слов из всех этих слов
     */
    public void setQuorum(int quorum) {
        if (quorum > 0 && quorum <= 10) {
            LOG.debug("setQuorum: " + quorum);
            urlGenerator.setParameter("quorum", quorum);
            this.quorum = quorum;
        }
    }

    /**
     * @param rating Устанавливает % влияния rating на ранжирование
     */
    public void setRating(int rating) {
        if (rating >= 0 && rating <= 100) {
            LOG.debug("setRating: " + rating);
            urlGenerator.setParameter("rating", rating);
            this.rating = rating;
        }
    }

    /**
     * @param country Фильтрация по полю "country"
     */
    public void filterCountry(int country) {
        if (country > 0) {
            LOG.debug("filterCountry: " + country);
            filters.append(" and country IN(0,").append(country).append(")");
        }
    }

    /**
     * @param shop Фильтрация по полю "shop"
     */
    public void filterShop(int shop) {
        if (shop > 0) {
            LOG.debug("filterShop: " + shop);
            urlGenerator.setParameter("shop", shop);
            filters.append(" and shop=").append(shop);
        }
    }

    /**
     * @param year Фильтрация по полю "year"
     */
    public void filterYear(int year) {
        if (year > 0) {
            LOG.debug("filterYear: " + year);
            urlGenerator.setParameter("year", year);
            filters.append(" and year=").append(year);
        }
    }

    /**
     * @param available Фильтрация по полю "available"
     */
    public void filterAvailable(boolean available) {
        if (available) {
            LOG.debug("filterAvailable: " + available);
            urlGenerator.setParameter("available", available);
            filters.append(" and available=").append(1);
        }
    }

    public void excludeId(int bookId) {
        filters.append(" and id!=").append(bookId);
    }

    public void resultWeight(String key, String keySphinx)
        throws SQLException {

        //urlGenerator.setParameter("key", key);
    }

    public boolean runSearch(String key, int skip, int limit) throws SQLException {
        urlGenerator.setParameter("key", key);
        float maxWeight = 0;
        String weightFormula = null;//", WEIGHT() AS weight";
        String match = " MATCH('\"" + key + "\"|(" + key + ")|\"" + key + "\"/" + quorum + "')";
        String option = " OPTION field_weights=(title=10, author=8, publisher=7);";

        Statement createStatement = sqlSphinx.createStatement();
        try (ResultSet resultSet = createStatement.executeQuery(
            "SELECT MAX(LENGTH(shop)) FROM Book WHERE" + match + filters + " LIMIT 1" + option)) {
            while (resultSet.next()) {
                maxWeight = resultSet.getFloat(1);
            }
            if (maxWeight > 0 && rating > 0) {
                weightFormula = ", ((WEIGHT()/" + maxWeight + "*(100-" + rating + ")) + (LENGTH(shop)/10)*" + rating + ") AS weight";
            }
            resultSet.close();
        }

        if (weightFormula == null)
            return false;

        String sql = "SELECT id, year" + weightFormula + " FROM Book WHERE" + match + filters
            + " ORDER BY weight DESC LIMIT " + skip + "," + limit + option;

        sql += "SHOW META;";
        sql += "SELECT @groupby AS shop, COUNT(*) AS count, country FROM Book WHERE " + match + " GROUP BY shop ORDER BY count DESC LIMIT 20";
        sphinxQuery(sql);
        return true;
    }

    private void sphinxQuery(String sql) throws SQLException {
        try (CallableStatement callableStatement = sqlSphinx.prepareCall(sql)) {
            boolean isResultSet = callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            if (isResultSet) {
                sphinxBooks(resultSet);
            }

            isResultSet = callableStatement.getMoreResults();
            if (isResultSet) {
                resultMeta(callableStatement.getResultSet());
            }

            isResultSet = callableStatement.getMoreResults();
            if (isResultSet) {
                sphinxShops(callableStatement.getResultSet());
            }
            callableStatement.close();
        } finally {
            //sphinxClose();
            LOG.debug("Sphinx Close Connect");
        }
    }


    private void sphinxBooks(ResultSet resultSet) throws SQLException {
        StringBuilder docsId = new StringBuilder();
        String separator = "";

        while (resultSet.next()) {
            years.put(resultSet.getInt("id"), normalizeYears(resultSet.getString("year")));
            docsId.append(separator);
            separator = ",";
            docsId.append(resultSet.getInt("id"));
        }
        resultSet.close();

        if (docsId.length() > 0) {
            LOG.debug("BooksId: " + docsId);
            booksId = docsId;
        }
    }

    private void resultMeta(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String key = resultSet.getString(1);
            String value = resultSet.getString(2);
            if ("total".equals(key)) {
                total = Integer.parseInt(value);
            }
            if ("total_found".equals(key)) {
                totalFound = Integer.parseInt(value);
            } else if ("time".equals(key)) {
                time = Float.parseFloat(value);
            }
        }
    }

    private void sphinxShops(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            LOG.debug("shop: " + resultSet.getObject(1) + ", count books: " + resultSet.getObject(2));
            shopsId.put(resultSet.getLong(1), resultSet.getLong(2));
        }
        resultSet.close();
    }

    private String normalizeYears(String string) {
        if (!StringUtils.isEmpty(string)) {
            return StringUtils.join(ArrayUtils.subarray(string.split(","), 0, 3), ", ");
        }
        return null;
    }

    private void sphinxClose() {
        try {
            if (sqlSphinx != null)
                sqlSphinx.close();
        } catch (SQLException ex) {
            LOG.error("", ex);
        }
    }

    public StringBuilder getBooksId() {
        return booksId;
    }

    public Map<Integer, String> getYears() {
        return years;
    }

    public LinkedHashMap<Long, Long> getShopsId() {
        return shopsId;
    }

    public long getTotal() {
        return total;
    }

    public long getTotalFound() {
        return totalFound;
    }

    public float getTime() {
        return time;
    }
}
