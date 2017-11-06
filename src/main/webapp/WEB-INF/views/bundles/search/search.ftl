<div class="srch">
    <div class="srch__wrapp">
        <div class="srch__stats">Найденно книг ${total} (${time?string["0.##"]} сек.)<#if nextPage.page gt 1>, страница ${nextPage.page}</#if>

        <#if shops?has_content>
            <div class="menu srch__menu">
                <span class="menu__btn srch__menu-btn">Магазины +</span>
                <div class="menu__content srch__menu-content">
                    <a href="/search/?key=${key!""}" class="menu__item srch__menu-item">Все</a>
                    <#list shops as shop>
                        <a href="/search/?key=${key!""}&shop=${shop.shopId}" class="menu__item srch__menu-item"
                           data-shop="${shop.shopId}">
                        ${shop.name} <i class="x">x</i> <i class="count">${shop.count}</i>
                        </a>
                    </#list>
                </div>
            </div>
        </#if>
        </div>

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

        <#if results?? && (nextPage.nextURL)??>
            <#include "/blocks/next-page/next-page.ftl">
        </#if>

        </section>
    </div>
</div>