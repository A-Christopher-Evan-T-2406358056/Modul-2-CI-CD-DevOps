# Laporan Refleksi

## Modul 1

### Refleksi 1

**Implementasi Backend dan Exception Handling**
Dimulai dari tingkat *low-level* atau *business logic* (serupa antara `removeById` dan `findById`), saya menggunakan
*custom checked exception* bernama `ProductNotFoundException`. Exception ini diterapkan pada fungsi *low-level*, di mana
tanggung jawab penanganan *exception* (handling) kemudian diteruskan ke *Controller*. Hal ini merupakan refleksi dari
pendekatan awal saya yang sebelumnya mengabaikan kasus sederhana seperti "produk tidak ditemukan" dan membiarkan program
mengalami terminasi begitu saja.

**Pemisahan Tanggung Jawab (Service vs. Repository)**
Saya mendiskusikan peran antara *Service* dan *Repository* serta berupaya agar interaksi keduanya terenkapsulasi dengan
baik. *Repository* difokuskan hanya untuk menerima atau memodifikasi data (persisitensi basis data). Awalnya, saya
langsung membuat fungsi *edit*, namun saya berusaha menjaga agar *Repository* tidak sekompleks *Service*. Setelah
melakukan *merge*, saya menyadari bahwa *Repository* seharusnya menangani seluruh fungsi CRUD. Dengan demikian,
*Service* dapat langsung memanggil fungsi `updateProduct`, alih-alih harus melakukan `findById` terlebih dahulu lalu
mengakses objek produk satu per satu.

**Penggunaan DTO (Data Transfer Object)**
Saya menemukan bahwa DTO adalah abstraksi data yang ideal untuk diterima oleh *Service* dari *Controller*. Meskipun saya
sudah menerapkannya pada ID untuk penghapusan dan data produk, saya belum mengubah *Service* untuk pembuatan (*created*)
karena bagian tersebut sudah disediakan oleh modul. Namun, jika direfleksikan kembali, bagian tersebut seharusnya
dimodifikasi agar sesuai dengan *best practice* pada Spring Boot.

**API dan Frontend**
Saya melakukan riset mengenai cara kerja *request mapping* dan *REST Controller* untuk membuat API. Saya menyadari bahwa
penggunaan AJAX untuk formulir sederhana sebenarnya adalah bentuk *overengineering*, sehingga formulir standar sudah
cukup.

Terkait fungsi *edit*, saya bereksperimen dengan *inline editing*. Saya menghadapi beberapa tantangan dalam proses ini.
Ide sederhananya adalah menggunakan *event* JavaScript `double click` untuk mengubah teks menjadi elemen *input*. Namun,
hal ini melibatkan penyesuaian lebar (*width*), *padding*, dan penyalinan isi teks. Karena terdapat beberapa kolom,
pendekatan awal saya adalah menyematkan ID dari *template engine* ke setiap sel. Namun, karena API membutuhkan ketiga
data sekaligus dan data saat ini (*current data*) sudah ada di tabel, saya menggunakan *query selector* untuk menemukan
baris (*row*) yang relevan, lalu mengambil nilai berdasarkan nama *field* datanya.

Pendekatan serupa saya terapkan untuk membuat API khusus tombol hapus (*delete*). Awalnya saya menggunakan DTO agar
*parsing* dari *body* JSON HTTP Request berjalan otomatis. Namun, saya menyadari bahwa untuk data sekecil ID, akan lebih
efisien jika diambil langsung dari parameter URL atau *header*, daripada harus melalui *body* request.

### Refleksi 2

**1. Mengenai Kecukupan Unit Test**
Untuk pertanyaan pertama, menurut saya cara menentukan kapan *unit test* dianggap cukup adalah dengan memahami definisi
*unit test* itu sendiri sebagai pengujian otomatis. Pola pikir yang digunakan adalah membandingkannya dengan apa yang
kita lakukan secara manual setelah menulis kode: "Apakah baris kode yang baru saya buat sudah aman?".

Memastikan *testing* sudah cukup memang sulit karena banyaknya kemungkinan kejadian pada perangkat lunak. Namun, standar
yang biasa digunakan adalah memikirkan setiap fitur dan mendaftar kemungkinan *bug* atau perilaku yang diharapkan dalam
situasi tertentu. Mengubah sudut pandang menjadi sisi pengguna (*user*) juga dapat membantu memprioritaskan apa yang
perlu diuji (terkait fungsionalitas dan kebutuhan dalam SDLC).

Perlu diingat bahwa 100% *code coverage* tidak menjamin bebas *bug*. *Unit test* yang buruk (misalnya sekadar
*copy-paste* output kode saat ini) akan tetap membuat kode tersebut "lulus" tes, meskipun tidak melakukan *assert* pada
bagian yang krusial. Selain itu, karena *code coverage* hanya memeriksa kode sumber saat ini, hal tersebut tidak
mencakup *bug* atau *error* yang mungkin timbul dari kode eksternal, seperti pustaka pihak ketiga maupun pustaka standar
Java.

**2. Mengenai Scope Functional Test**
Terkait pertanyaan kedua, menurut saya *test case* "memeriksa jumlah item di list" sebenarnya sudah termasuk dalam
cakupan kelas `CreateProductFunctionalTest.java`. Pengecekan jumlah item idealnya dilakukan setelah setiap fitur CRUD
dieksekusi. Refaktor ini membantu menyederhanakan kode (tanpa perlu menambahkan kelas baru) serta tetap menjaga
*coverage testing* yang baik. Hal ini menunjukkan bahwa untuk setiap kode yang ingin ditulis, kita sebaiknya memikirkan
cakupannya terlebih dahulu: apakah ini fitur baru, atau sekadar kode tambahan untuk melengkapi kode yang sudah ada.

## Modul 2

