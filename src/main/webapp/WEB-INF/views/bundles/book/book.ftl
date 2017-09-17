<div class="book" itemscope itemtype="http://schema.org/Book" data-bookid="${book.bookId}">

    <div class="book-image">
    <#if (book.image)??>
        <img class="book-image__src" src="${book.image}" width="200" border="0" itemprop="image">
    <#else>
        <img src="/images/no-image.gif" width="150" border="0">
    </#if>
    </div>


    <div class="book-price-default">
        <div class="book-price-default__price">
            87 руб.
            <div class="book-price-default__discount"></div>
        </div>

        <div class="book-price-default__saving">Вы экономите 23 руб.</div>
        <div class="book-price-default__shop">Лабиринт</div>
        <div class="book-price-default__buy">
            <a href="" class="btn_blue book-price-default__btn">В магазин</a>
        </div>
    </div>


    <div class="book__about">
        <h1 class="book__name" itemprop="name">${(book.title)!""}</h1>
    <#if (book.author)??>
        <div class="book__author" itemprop="author">${book.author}</div>
    </#if>
    <#if (book.binding)??>
        <div class="book__format"><b>Формат:</b> ${(book.binding)!""}</div>
    </#if>

    <#if (book.publisher)??>
        <div itemprop="publisher"><b>Издательство:</b> ${book.publisher}</div>
    </#if>
    <#if (book.series)??>
        <div itemprop="series"><b>Серия:</b> ${book.series}</div>
    </#if>
    <#if (book.age)??>
        <div><b>Возрастная категория:</b> <span itemprop="age">${book.age}</span></div>
    </#if>
    <#if (book.year)??>
        <div><b>Год издания:</b> ${class.year}</div>
    </#if>
    <#if (book.pageExtent)??>
        <div><b>Кол-во страниц:</b> <span itemprop="numberOfPages">${book.pageExtent}</span></div>
    </#if>
    <#if !book.isbnObject.empty>
        <#list book.isbnObject.readable as isbn>
            <div><b>ISBN-${isbn.id?length}:</b> <span itemprop="isbn">${isbn.code}</span></div>
        </#list>
    </#if>
    </div>

    <div class="clear"></div>

    <h3>Предложения магазинов</h3>
<#include "/blocks/price/price-book.ftl">

<#--    <h3>Дополнительные предложения</h3>
<#include "/blocks/book-relative/book-relative.ftl">
<#include "/blocks/book-relative/book-relative.ftl"-->


</div>