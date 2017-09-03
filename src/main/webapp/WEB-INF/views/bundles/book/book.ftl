<div class="book" itemscope itemtype="http://schema.org/Book" data-bookid="${book.bookId}">

    <div class="book-image">
    <#if (book.image)??>
        <img src="${book.image}" width="150" border="0" itemprop="image" class="book-image__file">
    <#else>
        <img src="/images/no-image.gif" width="150" border="0">
    </#if>
    </div>

    <h1 class="book__name" itemprop="name">${book.name}</h1>


<#include "/blocks/price/price-book.ftl">



</div>