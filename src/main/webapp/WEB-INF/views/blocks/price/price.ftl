<table class="price">
<#list res.prices as price>

    <tr class="price__row">
        <td class="price__cell price__cell_shop-name">${price.name}</td>
        <td class="price__cell price__cell_available">${price.available}</td>
        <td class="price__cell price__cell_year">${(price.year)!"-"}</td>
        <td class="price__cell price__cell_price">${price.priceFormat}</td>
        <td class="price__cell price__cell_buy">
            <a href="" class="btn_blue">В магазин</a>
        </td>
    </tr>


<#else>
    <div>Цены для данной книги отсутствуют</div>
</#list>
</table>