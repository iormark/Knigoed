<header id="header">
    <div class="header">
        <a href="/" class="header-logo">
            <img class="header-logo__image" src="http://www.knigoed.info/images/knigoed.png" width="120" height=""
                 alt="Knigoed - поиск книг">
        </a>

        <form class="header__country" method="POST" action="http://www.knigoed.info/svc/Service">
            <select class="header__select RU" name="country">
                <option value="RU" selected="">Россия</option>
                <option value="UA">Украина</option>
                <option value="BY">Белоруссия</option>
                <option value="KZ">Казахстан</option>
            </select>
            <input type="hidden" name="q" value="targetCountry">
        </form>

        <div class="header-panel">
            Вход
        </div>

        <form class="header-search" method="GET" action="/search">
            <div class="header-search__right">
                <input class="header-search__submit" type="submit" value="Поиск">
                <input type="hidden" name="available" value="true">
            </div>

            <div class="header-search__left">
                <div class="header-search__right">
                    <input class="header-search__year" type="text" name="year" value="" size="4" placeholder="2017">
                </div>
                <div class="header-search__left">
                    <input class="header-search__key" type="text" name="key" value="${key!""}"
                           placeholder="Введите название, автора, isbn...">
                </div>

            <#include "/blocks/srch-option/srch-option.ftl">
            </div>

        </form>

    </div>
</header>