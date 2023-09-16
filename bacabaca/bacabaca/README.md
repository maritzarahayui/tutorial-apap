---
## Tutorial 2
### What I have learned today
Model, Service, Controller, dan Gradle

### Pertanyaan
1. Apa itu DTO? Jelaskan kegunaannya pada proyek ini?
DTO adalah singkatan dari Data Transfer Object yang digunakan untuk mengirim data antara service dan controller. Pada proyek ini, DTO digunakan untuk menyimpan informasi mengenai sebuah buku.

2. Apa itu UUID? Mengapa UUID digunakan?
UUID adalah singkatan dari Universally Unique Identifier yang digunakan untuk mengidentifikasi objek atau entitas secara unik di berbagai sistem komputer. Contoh UUID adalah "550e8400-e29b-41d4-a716-446655440000". Singkatnya, UUID digunakan karena bisa menghasilkan identifikasi yang unik dan tahan lama untuk berbagai jenis entitas atau objek dalam sistem komputer yang berbeda-beda.

3. Pada service, mengapa perlu ada pemisahan antara interface dan implementasinya?
Pemisahan antara interface dan implementasinya diperlukan agar kita dapat mengembangkan kode mengacu kepada interface tanpa perlu menunggu implementasi selesai. Hal ini juga memudahkan penyediaan variasi service yang berbeda, di mana kita dapat memiliki beberapa implementasi yang berbeda dari interface yang sama untuk kebutuhan yang berbeda. Pemisahan ini mendukung pengembangan paralel dalam tim.

4. Menurut kamu anotasi @Autowired pada class Controller tersebut merupakan implementasi dari konsep apa? Dan jelaskan secara singkat cara kerja @Autowired tersebut dalam konteks service dan controller yang telah kamu buat.
Anotasi @Autowired yang digunakan pada class Controller tersebut merupakan implementasi dari konsep Dependency Injection.
Cara kerja @Autowired:
- Pada class `BukuService`, anotasi @Autowired digunakan pada atribut private BukuService bukuService. Saat aplikasi dijalankan, Spring Container akan mencari bean yang sesuai dengan tipe BukuService yang dideklarasikan. Setelah menemukan bean tersebut (di sini BukuServiceImpl), Spring Container secara otomayttis akan meng-inject instance bean ke dalam atribut bukuService.
- Pada class `BukuController`, anotasi @Autowired juga digunakan untuk injeksi class BukuService. Ketika permintaan HTTP yang membutuhkan layanan dari BukuService diterima, Spring Controller akan menggunakan instance bukuService yang sudah diinjeksi. Spring Controller dapat memanggil metode-metode dari bukuService tanpa harus membuat objek BukuService secara manual.

5. Apa perbedaan @GetMapping dan @PostMapping?
`@GetMapping` digunakan untuk permintaan GET yang membaca data, sementara `@PostMapping` digunakan untuk permintaan POST yang melakukan perubahan atau mengirim data ke server.

6. Jelaskan proses yang terjadi di controller, model, dan service pada proses create buku, mulai dari fungsi formAddBuku hingga pengguna menerima halaman success-create-buku.
- BukuController
Ketika pengguna mengakses URL `/buku/create`` dengan metode HTTP GET, Controller menerima permintaan ini melalui metode formAddBuku. Di dalam metode formAddBuku, Controller membuat objek BukuDTO yang digunakan sebagai data isian form yang akan ditampilkan kepada pengguna. Ini dilakukan dengan membuat instance BukuDTO dan menambahkannya ke model dengan nama "bukuDTO" menggunakan model.addAttribute(). Setelah menyiapkan data form, Controller mengembalikan string "form-create-buku".
- form-create-buku
Template form-create-buku akan merender halaman HTML form yang berisi input untuk data buku seperti judul, penulis, tahun terbit, dan harga. Data ini diambil dari objek BukuDTO yang telah dimasukkan ke model. Pengguna dapat mengisi form yang tersedia pada halaman ini.
- BukuController
Ketika pengguna mengisi form dan mengirimkannya dengan metode POST, Controller menerima permintaan ini melalui metode addBuku. Di dalam metode addBuku, Controller pertama-tama menghasilkan UUID baru untuk buku yang akan dibuat dengan menggunakan UUID.randomUUID() yang akan menjadi ID unik untuk buku yang akan disimpan. Kemudian, Controller memeriksa apakah judul buku yang akan dibuat sudah ada dalam daftar buku yang ada menggunakan metode isJudulExist() dari BukuService. Jika judul sudah ada, maka Controller menambahkan pesan kesalahan ke model dan mengembalikan string "error-view". Ini akan mengarahkan pengguna ke halaman error. Jika judul belum ada, Controller membuat objek Buku dengan data yang diambil dari BukuDTO yang dikirim dari form. Kemudian, Controller memanggil metode createBuku() dari BukuService untuk menyimpan buku baru tersebut. Setelah buku berhasil dibuat, Controller menambahkan ID dan judul buku ke model untuk ditampilkan pada halaman success-create-buku. Controller juga akan mengembalikan string "success-create-buku".
- success-create-buku
Template success-create-buku akan merender halaman yang mengonfirmasi kepada pengguna bahwa buku telah berhasil dibuat. Informasi ID dan judul buku yang telah dibuat akan ditampilkan kepada pengguna sehingga pengguna akan melihat halaman ini sebagai respons bahwa buku berhasil dibuat.

7. Jelaskan mengenai th:object!
`th:object` adalah atribut yang digunakan untuk mengaitkan objek Java dengan elemen HTML tertentu dalam template Thymeleaf. Atribut ini dapat digunakan dalam elemen seperti <form>, <div>, atau elemen lain yang membutuhkan data objek. Atribut ini memungkinkan kita untuk menghubungkan data objek dengan elemen-elemen HTML sehingga dapat melakukan rendering data dinamis atau pembaruan data saat pengguna berinteraksi dengan halaman web.

8. Jelaskan mengenai th:field!
TIPS: Buka “view page source” dari halaman Tambah Buku di browser. Lakukan screenshot kemudian jelaskan temuan kalian.
`th:field` adalah atribut yang digunakan untuk mempermudah binding data objek ke elemen HTML form, khususnya dalam form handling. Atribut ini akan menghasilkan atribut `name`` dan `id` dalam elemen HTML input atau elemen form lainnya secara otomatis berdasarkan nama properti objek yang terkait dengan form tersebut. Dengan kata lain, atribut ini membuat binding antara properti objek dan elemen HTML sehingga saat data form dikirimkan, Thymeleaf akan dapat mengenali dan menghubungkan data tersebut dengan objek Java yang sesuai.

![Alt text](<Screenshot (3723).png>)

Berdasarkan screenshot tersebut, terdapat perubahan pada kode html yang semula th:field="*{judul}". Contohnya, pada line 20 berubah menjadi id="judul" name="judul" value="", line 24 berubah menjadi id="penulis" name="penulis" value="", line 28 berubah menjadi id="tahunTerbit" name="tahunTerbit" value="", dan line 32 berubah menjadi id="harga" name="harga" value="0".

### What I did not understand
- [ ] Kalo @GetMapping atau @PostMapping dll nya ga ditulis emang ngaruh?
