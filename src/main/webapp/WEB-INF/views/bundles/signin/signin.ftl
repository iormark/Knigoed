<div class="signin-wrap">
    <span class="signin-helper"></span>
    <div class="signin">
        <h2 class="signin__title">Добро пожаловать!</h2>
        <p class="signin__description"></p>

        <form method="post" action="/signin" class="signin-form" id="signin-form">
            <div class="message"></div>
            <input type="email" name="username" placeholder="Email пользователя" class="signin-form__input">
            <input type="password" name="password" placeholder="Пароль" class="signin-form__input">
            <input type="hidden" name="remember-me" value="on">
            <input class="btn btn_green signin__btn" type="submit" value="Войти">
        </form>

        <div class="signin-footer">
            <a href="/signin/password" class="signin-footer__password">напомнить?</a>
        </div>
    </div>
</div>