
[![](https://img.shields.io/badge/1.14.3-Jsoup-blue)](https://mvnrepository.com/artifact/org.jsoup/jsoup/1.14.3)

# Android Glisemik Uygulaması
Bu uygulama jsoup kütüphanesi kullanılarak bir internet sitesinden bazı besinlerin "Glisemik indeksi,Karbonhidrat miktarı ve Kalori" değerlerini uygulamaya çekerek lokal veritabanına kaydı gerçekleştirilmiştir. Veritabanı olarak SQLite kullanılmıştır. Veritabanına kayıt edilen verilere uygulama içinde yeni veriler eklenebilir, kayıtlı veriler silinebilir ve veriler üzerinde düzenlemeler yapılabilir.

# Uygulama Başlangıcı ve Ana Sayfa
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/1.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/1.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/2.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/2.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/3.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/3.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/4.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/4.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/5.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/5.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/6.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/6.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/14.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/14.png" width="200" style="max-width:100%;"></a>

Uygulama ana sayfasında veritabanında kayıtlı olan veriler listeye dönüştürülerek kullanıcıya gösterilmiştir. İtemlerden herhangi birine tıklanıldığı zaman kullanıcıyı ürün üzerinde güncelleme işlemleri yapabileceği pencereye yönlendirir ve itemlere uzun basma işleminde ise kullanıcıya bu veriyi kalıcı olarak silebileceği pencere açılmaktadır.  Ana sayfa da bulunan Spinner yapısı ile birlikte veritabanında kayıtlı kategoriler arasında dolaşma işlemleri gerçekleştirilmiştir. Liste de bulunan itemler içinde arama yapma işlemi gerçekleştirebilmek için ise Search Box yapısı kullanılmıştır.

# Kategoriler

<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/7.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/7.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/8.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/8.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/9.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/9.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/13.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/13.png" width="200" style="max-width:100%;"></a>

Kategoriler sayfasında uygulamanın veritabanında kayıtlı olan kategoriler listelenmiştir. Bu sayfada yeni kategori ekleme, kayıtlı kategoriyi düzenleme ve kategori silme işlemleri gerçekleştirilebilmektedir. Kullanıcı bir kategoriyi silmek istiyorsa bu kategori içerisinde kayıtlı ürün bulunmamak zorundadır.

# Yeni Ürün Ekleme

<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/10.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/10.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/11.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/11.png" width="200" style="max-width:100%;"></a>
<a href="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/12.png" target="_blank">
<img src="https://github.com/Eraycn16/Android-Glisemik-Uygulamasi/blob/main/img/12.png" width="200" style="max-width:100%;"></a>

Bu sayfa da kullanıcı veritabanına yeni ürün kayıtı işlemi gerçekleştirebilir. Kullanıcı yeni ürün kaydı yapabilmek için kullanıcıdan istenen bütün alanları doldurmak zorundadır. Bu alanları doldurmaz ise kullanıcıya uyarılar geri döndürülmüştür.
