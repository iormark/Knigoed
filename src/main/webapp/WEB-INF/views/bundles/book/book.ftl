<div id="book" class="book" itemscope itemtype="http://schema.org/Book" data-bookid="${book.bookId}">

    <div class="book-image">
    <#if (book.image)??>
        <img src="${book.image}" width="150" border="0" itemprop="image" class="book-image__file">
    <#else>
        <img src="/images/no-image.gif" width="150" border="0">
    </#if>
    </div>

    <h1 class="book__name" itemprop="name">${book.name}</h1>

    <table>
        <tr>
            <th>Магазин</th>
            <th>Наличие</th>
            <th>Год изд.</th>
            <th>Цена</th>
        </tr>
    <#list prices as price>
        <tr>
            <td>${price.shopName}</td>
            <td>${price.available}</td>
            <td>${price.year}</td>
            <td>${price.price}</td>
        </tr>
    <#else>
        <tr>
            <td colspan="4">Part executed when there are 0 items</td>
        </tr>
    </#list>
    </table>

</div>