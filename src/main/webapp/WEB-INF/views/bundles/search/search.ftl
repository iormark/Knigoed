<div class="srch">
    <div class="srch__stats">Найденно книг ${total} (${time?string["0.##"]} сек.)</div>
<!--
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
-->

    <section class="srch-result">

    <#list results as res>
        <div class="srch__item" data-id="${res.bookId}">

            <a class="srch__image" href="/book/${res.bookId}" target="_blank">
                <#if (res.image)??>
                    <img src="${(res.image)!''}" class="srch__image">
                <#else>
                    <img src="/images/no-image.gif">
                </#if>
            </a>

            <div class="srch__info">
                <h3 class="srch-title">
                    <a class="srch-title__name" href="/book/${res.bookId}" target="_blank">${(res.title)!""}</a><br>
                    <span class="srch-title__author">${(res.author)!""} <#if (res.publisher)??>(${res.publisher}
                        )</#if></span>
                </h3>

                <div class="srch__desc">
                    <#if (res.series)??>Серия: ${res.series}<br></#if>
                    <#if (res.isbn)??>ISBN: ${res.isbn}<br></#if>
                    <p><#if (res.description)??>${res.description}</#if></p>
                </div>
            </div>

            <div class="srch__prices">
                <#include "/blocks/price/price.ftl">
            </div>
            <div class="srch__split"></div>
        </div>


    <#else>
        <div class="srch-message">
            <p class="srch-message__title">В Книгоеде нет такой книги.</p>
            <p class="srch-message__message">Попробуйте проверить запрос на наличие ошибок или сформулировать его
                по-другому.</p>
        </div>
    </#list>

    </section>

</div>