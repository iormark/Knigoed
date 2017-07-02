ALTER TABLE knigoed.book RENAME TO knigoed.Book;
ALTER TABLE knigoed.book_info RENAME TO knigoed.BookInfo;
ALTER TABLE knigoed.book_isbn_id RENAME TO knigoed.BookIsbnId;
ALTER TABLE knigoed.book_price RENAME TO knigoed.BookPrice;
ALTER TABLE knigoed.book_shop_id RENAME TO knigoed.BookShopId;
ALTER TABLE knigoed.country RENAME TO knigoed.Country;
ALTER TABLE knigoed.currencies RENAME TO knigoed.Currency;
ALTER TABLE knigoed.payment RENAME TO knigoed.Payment;
ALTER TABLE knigoed.settings RENAME TO knigoed.Setting;
ALTER TABLE knigoed.shop RENAME TO knigoed.Shop;
ALTER TABLE knigoed.shop_pid RENAME TO knigoed.ShopPartnerId;
ALTER TABLE knigoed.shop_stat RENAME TO knigoed.ShopStatistic;
ALTER TABLE knigoed.shop_target RENAME TO knigoed.ShopTarget;
ALTER TABLE knigoed.users RENAME TO knigoed.User;

# Book
ALTER TABLE knigoed.Book CHANGE page_extent pageExtent SMALLINT(6) unsigned;
ALTER TABLE knigoed.Book CHANGE last_modified lastModified DATETIME COMMENT 'Метка последнего обновления книги';
ALTER TABLE knigoed.Book CHANGE picture image VARCHAR(255);
ALTER TABLE knigoed.Book CHANGE `code` isbn VARCHAR(255);

# BookIsbnId
ALTER TABLE knigoed.BookIsbnId CHANGE `code` isbn VARCHAR(13);

# BookInfo
ALTER TABLE knigoed.BookInfo CHANGE `text` description MEDIUMTEXT;

# BookPrice
ALTER TABLE knigoed.BookPrice CHANGE rsi priceId VARCHAR(36) NOT NULL;
ALTER TABLE knigoed.BookPrice CHANGE sid shopBookid VARCHAR(20) NOT NULL;
ALTER TABLE knigoed.BookPrice CHANGE currencyId currencyCode VARCHAR(3) DEFAULT 'RUR' COMMENT 'Идентификатор валюты';

# BookShopId
ALTER TABLE knigoed.BookShopId CHANGE sid shopBookid VARCHAR(20) NOT NULL;
ALTER TABLE knigoed.BookShopId CHANGE shop shopId SMALLINT(6) NOT NULL;

# Country
ALTER TABLE knigoed.Country CHANGE country_id countryId INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID страны';
ALTER TABLE knigoed.Country CHANGE country_name countryName VARCHAR(55) NOT NULL COMMENT 'Название страны';
ALTER TABLE knigoed.Country CHANGE country_code countryCode CHAR(4) NOT NULL COMMENT 'Список кодов стран по ISO 3166';
ALTER TABLE knigoed.Country CHANGE currency_code currencyCode CHAR(3) NOT NULL COMMENT 'Код валюты по ISO 4217';
ALTER TABLE knigoed.Country CHANGE currency_suffix currencySuffix CHAR(4) NOT NULL COMMENT 'Окончание у цены';

# Currency
ALTER TABLE knigoed.Currency CHANGE id currencyFrom VARCHAR(3) NOT NULL;
ALTER TABLE knigoed.Currency CHANGE currency currencyTo VARCHAR(3) COMMENT 'По отношению к этой валюте';
ALTER TABLE knigoed.Currency CHANGE value currencyValue DECIMAL(10,4) NOT NULL DEFAULT '0.0000';

# Shop
ALTER TABLE knigoed.Shop CHANGE country countryCode VARCHAR(2) COMMENT 'Страна магазина / TODO: сделать связь с Country';
ALTER TABLE knigoed.Shop CHANGE last_modified lastModified DATETIME COMMENT 'Последнее изменение каталога';
ALTER TABLE knigoed.Shop CHANGE last_update lastUpdate DATETIME COMMENT 'Последнее обновление базы';
ALTER TABLE knigoed.Shop CHANGE creation_date creationDate DATE;
ALTER TABLE knigoed.Shop CHANGE json_meta setting TEXT COMMENT 'Метаинформация в формате JSON';

# ShopPartnerId
ALTER TABLE knigoed.ShopPartnerId CHANGE pid partnerIdXml VARCHAR(255) COMMENT 'Устаревший формат';
ALTER TABLE knigoed.ShopPartnerId CHANGE partnerId partnerIdJson VARCHAR(155) COMMENT 'Партнерские ID в формате JSON';

# ShopStatistic
ALTER TABLE knigoed.ShopStatistic CHANGE id statisticId INT(10) unsigned NOT NULL AUTO_INCREMENT;
ALTER TABLE knigoed.ShopStatistic CHANGE `date` creationDate DATETIME;
ALTER TABLE knigoed.ShopStatistic CHANGE info clientInfo TEXT NOT NULL COMMENT 'Инфо о клиенте';
ALTER TABLE knigoed.ShopStatistic CHANGE whence fromUrl VARCHAR(255) COMMENT 'Откуда';
ALTER TABLE knigoed.ShopStatistic CHANGE whither toUrl TEXT NOT NULL COMMENT 'Куда';
ALTER TABLE knigoed.ShopStatistic CHANGE `value` price DECIMAL(8,2) NOT NULL DEFAULT '0.00' COMMENT 'Стоимость перехода';

# ShopTarget
ALTER TABLE knigoed.ShopTarget CHANGE code countryCode VARCHAR(2) COMMENT 'Таргетинг / TODO: сделать связь с Country';

# User
ALTER TABLE knigoed.User CHANGE last_login lastLogin TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Отметка времени для последнего входа пользователя.';











