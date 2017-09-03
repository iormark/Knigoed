<div class="srch-option">
    <a class="srch-option__type current" href="/search/?q=java" data-type="all">Все</a>
    <a class="srch-option__type" href="/search/?q=java&amp;type=book" data-type="book">Бумажные</a>
    <a class="srch-option__type" href="/search/?q=java&amp;type=ebook" data-type="ebook">Электронные</a>
    <a class="srch-option__type" href="/search/?q=java&amp;type=audiobook" data-type="audiobook">Аудиокниги</a>

<#if shops?has_content>
    <ul class="srch-option-shop">
        <li class="srch-option__shop"><a href="">Все</a></li>
        <#list shops as shop>
            <li class="srch-option__shop" data-shop="${shop.shopId}"><a href="">${shop.name}</a></li>
        </#list>
    </ul>
<#else>
    <div class="srch-message">
        Нет магазинов.
    </div>
</#if>

    <input id="srch-option-type" type="hidden" name="type" value="all">
    <input id="srch-option-shop" type="hidden" name="shop" value="">
</div>