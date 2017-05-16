package info.knigoed.dao;

import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
import info.knigoed.pojo.Shop;
import info.knigoed.util.Jackson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.ResultSetIterable;
import org.sql2o.Sql2o;

@Service
public class BookDao {

	@Autowired
	private Sql2o sql2o;

	public Book readBook(int bookId) {
		String sql = "SELECT b.bookId, b.title, b.author, b.publisher, b.series,  b.page_extent AS pageExtent, b.binding, "
				+ "b.code AS isbn, b.age, b.picture AS image, b.edit, b.last_modified AS lastModified, bi.text "
				+ "FROM book b, book_info bi WHERE b.bookId = bi.bookId AND b.bookId = :bookId  AND b.edit != 'delete'";

		try (Connection con = sql2o.open()) {
			return con.createQuery(sql).addParameter("bookId", bookId)
					.executeAndFetchFirst(Book.class);
		}
	}

	public List<Price> readPrices(int bookId, String country, String sortPrice, int limit) throws IOException {
		String sql = "SELECT s.shopId, "
				+ "s.name AS shopName, s.domain, s.json_meta AS shopSettings, s.country, p.rsi, p.bookId, "
				+ "p.url, p.price, p.currencyId, p.available, p.downloadable, p.year "
				+ "FROM book_price p, shop s "
				+ "LEFT JOIN shop_target target ON s.shopId=target.shopId "
				+ "WHERE "
				+ "s.status IN('pause', 'process') "
				+ "AND (target.code = :code OR target.code IS NULL) "
				+ "AND p.shopId = s.shopId "
				+ "AND p.bookId = :bookId LIMIT :limit";

		try (Connection con = sql2o.open()) {
			try (ResultSetIterable<Price> prices = con.createQuery(sql).addParameter("code", country)
					.addParameter("bookId", bookId).addParameter("limit", limit).executeAndFetchLazy(Price.class)) {
				return rewritePrice(prices);
			}
		}
	}

	private List<Price> rewritePrice(ResultSetIterable<Price> prices) throws IOException {
		List<Price> list = new ArrayList();
		for (Price price : prices) {
			
			price.setShopName(StringUtils.isEmpty(price.getShopName()) ? price.getDomain() : price.getShopName());
			
			price.setCurrencies(Jackson.mapper().readValue(price.getShopSettings(), Shop.Settings.class).getCurrencies());

			list.add(price);
		}
		return list;
	}
}
