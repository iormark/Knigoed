<div class="price">

    <!--<div class="price__row">
        <div class="price__cell price__cell_shop-name">Магазин</div>
        <div class="price__cell price__cell_available">Наличие</div>
        <div class="price__cell price__cell_year">Год изд.</div>
        <div class="price__cell price__cell_price">Цена</div>
        <div class="price__cell price__cell_buy">
        </div>
    </div>-->

<#list prices as price>

    <div class="price__row">
        <div class="price__cell price__cell_shop-name">${price.name}</div>
        <div class="price__cell price__cell_available">${price.available}</div>
        <div class="price__cell price__cell_year">${price.year}</div>
        <div class="price__cell price__cell_price">${price.priceFormat}</div>
        <div class="price__cell price__cell_buy">
            <a href="" class="btn_blue">В магазин</a>
        </div>
    </div>

<#else>
    <div>Цены для данной книги отсутствуют</div>
</#list>
</div>