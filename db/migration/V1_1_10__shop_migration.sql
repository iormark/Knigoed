UPDATE knigoed.Shop SET lastModified = '2014-08-22 00:00:00' WHERE shopId IN (21,30,36,47);
ALTER TABLE knigoed.Shop MODIFY userId INT(10) UNSIGNED NOT NULL;
