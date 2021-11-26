# Dorayaki Supplier (SOAP)
## Semester I Tahun 2021/2022 
### Tugas Besar II IF3110 Milestone 1 Pengembangan Aplikasi Berbasis Web

*Program Studi Teknik Informatika* <br />
*Sekolah Teknik Elektro dan Informatika* <br />
*Institut Teknologi Bandung* <br />

*Semester I Tahun 2021/2022*

## Deskripsi
Dorayaki-Supplier merupakan Web Service berbasis protokol SOAP yang menyediakan layanan untuk mengekspos beberapa fungsi yang dimiliki oleh pabrik dorayaki (Dorayaki Factory Server) kepada toko dorayaki (Dorayaki Store), intinya Dorayaki-Supplier adalah interface yang menghubungkan kedua service tersebut. Web Service ini dibangun dengan menggunakan JAX WS dan di-deploy dengan menggunakan maven.

## Fungsional Aplikasi
1. Membaca nama varian dorayaki yang disediakan pabrik
2. Membaca status request penambahan stok dorayaki
3. Menambah request penambahan stok dorayaki
4. Rate limiter

## Author
1. Gde Anantha Priharsena (13519026)
2. Reihan Andhika Putra (13519043)
3. Reyhan Emyr Arrosyid (13519167)

## Requirements
- [AdoptOpenJDK 11](https://adoptopenjdk.net/)
- [Maven 3.8.4](https://maven.apache.org/download.cgi)

## Cara menjalankan
1. Download semua requirement yang dibutuhkan
2. Lakukan instalasi AdoptOpenJDK 11 sesuai petunjuk di [sini](https://adoptopenjdk.net/installation.html) dan lakukan instalasi Maven sesuai petunjuk di [sini](https://www.javatpoint.com/how-to-install-maven)
3. Clone repository ini dan masuk ke direktori dssupplier
4. Ketikkan command 
```
mvn install
mvn clean compile assembly:single
java -jar ./target/dssupplier-1.0-SNAPSHOT-jar-with-dependencies.jar
```
5. Jika terjadi error(kadang suka aneh emang) maka anda bisa install extension java untuk vscode dan lakukan klik 'run' di App.java
6. Pada command prompt akan terlihat url dari tiap WSDL service yang anda bisa buka satu persatu
7. Testing API bisa menggunakan [SOAPUI](https://www.soapui.org/downloads/soapui/) atau [wizdler](https://chrome.google.com/webstore/detail/wizdler/oebpmncolmhiapingjaagmapififiakb)

## Service 
1. Dorayaki
```
POST http://localhost:6123/ds/dorayaki?wsdl
```
Service
- getAllDorayakiName: Mendapatkan semua nama resep dorayaki yang ada di pabrik
2. Log Request
```
POST http://localhost:6123/ds/log-request?wsdl
```
Service
- addLogRequest: Menambah log request baru serta memanggil endpoint di pabrik untuk menambah request baru
- createLogRequestTable: Membuat tabel log request (bisa dilakukan di backend pabrik juga)
3. Request
```
POST http://localhost:6123/ds/request?wsdl
```
Service
- syncRequest: Membaca status request yang sudah diajukan toko dan mengabari status mana saja yang sudah di acc namun belum diketahui toko
  
## Basis Data Terkait
### Tabel Log Request
| Atribut     | Tipe |
| ----------- | ----------- |
| id      | char(36)       |
| ip   | varchar(255)        |
| endpoint   | varchar(255)        |
| timestamp   | datetime        |

## Pembagian Kerja
- Dorayaki Service : 13519043, 13519167
- Log Request Service : 13519043
- Request Service : 13519043, 13519167
- Rate Limiting : 13519043 
- Database Handling & Other Utility : 13519043