<header id="head" class="head">
    <div class="head__wrap">
        <div class="head-logo">
            <a href="/" class="head-logo__image">
                <!--<img class="head-logo__image" src="http://www.knigoed.info/images/knigoed.png" width="120" height=""
                     alt="Knigoed - поиск книг">-->
                Knigoed ▸
            </a>

            <form class="head-logo__country" method="POST" action="/setting/country">
                <select class="head-logo__select ${countryCode}" name="country">
                <#list countries as country>
                    <option value="${country.countryCode}"<#if country.selected>selected="selected"</#if>>
                    ${country.countryName}
                    </option>
                </#list>
                </select>
            </form>
        </div>

        <div class="menu head-panel">
            <div class="menu__btn head-panel__menu-btn"><#if user??>${user.name}<#else>Меню +</#if></div>
            <div class="menu__content head-panel__menu-content">
            <#if user??>
                <a href="/profile" class="menu__item">Профиль</a>
                <a href="/shop" class="menu__item">Мои магазины</a>
                <a href="/shop/add" class="menu__item">Добавить магазин</a>
            <#else>
                <a href="/signin" class="menu__item">Магазинам</a>
                <a href="/signin" class="menu__item">Войти</a>
                <a href="/signup" class="menu__item">Регистрация</a>
            </#if>
                <div class="menu__footer">
                    <a href="/api-prices.html" class="menu__item">API-price</a>
                    <a href="/isbn-issn.html" class="menu__item">Что такое ISBN?</a>
                    <a href="/book-format.html" class="menu__item">Форматы книг</a>
                    <a href="/last" class="menu__item">Последние поступления</a>
                </div>
            </div>
        </div>

        <form class="head-search" method="GET" action="/search">
            <div class="head-search__right">
                <input class="head-search__submit" type="submit" value="Поиск">
                <input type="hidden" name="available" value="true">
            </div>

            <div>
                <div class="head-search__right">
                    <input class="head-search__year" type="text" name="year" <#if year??>value="${year}"</#if> size="4"
                           placeholder="2017">
                </div>
                <div class="head-search__left">
                    <input class="head-search__key" type="text" name="key" value="${key!""}"
                           placeholder="Введите название, автора, isbn...">
                </div>

                <div class="head-search__option">
                    <a class="head-search__type" href="/search/?key=java" data-type="all"><span>Все</span></a>
                    <a class="head-search__type" href="/search/?key=java&amp;type=book" data-type="book"><span>Бумажные</span></a>
                    <a class="head-search__type" href="/search/?key=java&amp;type=ebook" data-type="ebook"><span>Электронные</span></a>
                    <a class="head-search__type" href="/search/?key=java&amp;type=audiobook" data-type="audiobook"><span>Аудиокниги</span></a>

                    <input id="head-search-type" type="hidden" name="type" value="all">
                    <input id="head-search-shop" type="hidden" name="shop" value="">
                </div>
            </div>
        </form>

    </div>
</header>