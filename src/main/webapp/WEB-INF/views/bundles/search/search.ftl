<div class="srch">

    <div class="srch__stats">Найденно книг 1 600 (0,42 сек.)</div>

    <section class="srch-result">

    <#list result as res>

        <div class="srch__item" data-id="${res.bookId}">

            <a class="srch__image" href="/book/${res.bookId}" target="_blank">
                <#if (res.image)??>
                    <img src="${(res.image)!''}" class="srch__image">
                <#else>
                    <img src="/images/no-image.gif">
                </#if>
            </a>

            <div class="srch__info">
                <h3 class="srch__title">
                    <a class="title" href="/book/${res.bookId}" target="_blank">${(res.title)!""}</a><br>
                    <span>${(res.author)!""} <#if (res.publisher)??>(${res.publisher})</#if></span>
                </h3>

                <div class="srch__desc">
                    <#if (res.series)??>Серия: ${res.series}<br></#if>
                    <#if (res.years)??>Год: ${res.years}<br></#if>
                    <#if (res.code)??>ISBN: ${res.code}<br></#if>
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