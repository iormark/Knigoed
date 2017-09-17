#!/bin/sh

read -p "User: " user
read -p "Password: " password
base=knigoed1

for table in "book" "book_info" "book_price" "book_isbn_id" "book_shop_id"
do
    echo "Create "$table
    mysqldump -u $user -p$password \
    --where "1=1 ORDER BY bookId ASC LIMIT 30000" $base $table > $base"_"$table".sql"
done

echo "Create country"
mysqldump -u $user -p$password $base country > $base"_country.sql"
echo "Create currencies"
mysqldump -u $user -p$password $base currencies > $base"_currencies.sql"
echo "Create payment"
mysqldump -u $user -p$password $base payment > $base"_payment.sql"
echo "Create settings"
mysqldump -u $user -p$password $base settings > $base"_settings.sql"
echo "Create shop"
mysqldump -u $user -p$password $base shop > $base"_shop.sql"
echo "Create shop_pid"
mysqldump -u $user -p$password $base shop_pid > $base"_shop_pid.sql"
echo "Create shop_stat"
mysqldump --no-data -u $user -p$password knigoed1 shop_stat > $base"_shop_stat.sql"
echo "Create shop_target"
mysqldump -u $user -p$password $base shop_target > $base"_shop_target.sql"
echo "Create users"
mysqldump -u $user -p$password $base users > $base"_users.sql"
echo "Create $base.tar.gz"
tar -zcpf $base".tar.gz" *.sql
echo "Remove "$base"_*.sql"
rm $base"_"*.sql