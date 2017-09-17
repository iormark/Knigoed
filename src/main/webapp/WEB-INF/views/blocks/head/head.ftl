<header id="head">
    <div class="head">
        <a href="/" class="head-logo">
            <!--<img class="head-logo__image" src="http://www.knigoed.info/images/knigoed.png" width="120" height=""
                 alt="Knigoed - поиск книг">-->
            Knigoed ▸
        </a>

        <form class="head__country" method="POST" action="/setting/country">
            <select class="head__select ${countryCode}" name="country">
            <#list countries as country>
                <option value="${country.countryCode}"<#if country.selected>selected="selected"</#if>>
                ${country.countryName}
                </option>
            </#list>
            </select>
        </form>

        <div class="head-panel">
            <#if user??>
                <div class="head-panel__icon">${user.name}</div>
            <#else>
                <div class="head-panel__icon">Меню</div>
            </#if>

        </div>

        <form class="head-search" method="GET" action="/search">
            <div class="head-search__right">
                <input class="head-search__submit" type="submit" value="Поиск">
                <input type="hidden" name="available" value="true">
            </div>

            <div class="head-search__left">
                <div class="head-search__right">
                    <input class="head-search__year" type="text" name="year" <#if year??>value="${year}"</#if> size="4"
                           placeholder="2017">
                </div>
                <div class="head-search__left">
                    <input class="head-search__key" type="text" name="key" value="${key!"программирование"}"
                           placeholder="Введите название, автора, isbn...">
                </div>

                <div class="head-search__option">
                    <a class="head-search__type" href="/search/?q=java" data-type="all"><span>Все</span></a>
                    <a class="head-search__type" href="/search/?q=java&amp;type=book"
                       data-type="book"><span>Бумажные</span></a>
                    <a class="head-search__type" href="/search/?q=java&amp;type=ebook" data-type="ebook"><span>Электронные</span></a>
                    <a class="head-search__type" href="/search/?q=java&amp;type=audiobook" data-type="audiobook"><span>Аудиокниги</span></a>

                    <input id="head-search-type" type="hidden" name="type" value="all">
                    <input id="head-search-shop" type="hidden" name="shop" value="">
                </div>
            </div>
        </form>

    </div>
</header>