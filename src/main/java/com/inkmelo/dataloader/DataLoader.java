package com.inkmelo.dataloader;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.inkmelo.book.Book;
import com.inkmelo.book.BookRepository;
import com.inkmelo.book.BookStatus;
import com.inkmelo.category.Category;
import com.inkmelo.category.CategoryRepository;
import com.inkmelo.category.CategoryStatus;
import com.inkmelo.genre.Genre;
import com.inkmelo.genre.GenreRepository;
import com.inkmelo.genre.GenreStatus;
import com.inkmelo.publisher.Publisher;
import com.inkmelo.publisher.PublisherRepository;
import com.inkmelo.publisher.PublisherStatus;

@Component
@Profile(value = "devv")
public class DataLoader implements CommandLineRunner {
	
	private final GenreRepository genreRepository;
	private final CategoryRepository categoryRepository;
	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;
	private Collection<String> searchList = new ArrayList<>(); 

	public DataLoader(GenreRepository genreRepository, CategoryRepository categoryRepository,
			BookRepository bookRepository, PublisherRepository publisherRepository) {
		super();
		this.genreRepository = genreRepository;
		this.categoryRepository = categoryRepository;
		this.bookRepository = bookRepository;
		this.publisherRepository = publisherRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		
//		========================================= Load initial data into the Category database ============================================
		categoryRepository.save(Category.builder()
				.name("Sách nói")
				.description("Sách nói, sách audio định dạng mp3")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(CategoryStatus.ACTIVE)
				.build());
		
		categoryRepository.save(Category.builder()
				.name("Sách bản cứng")
				.description("Sách bản cứng, sách giấy")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(CategoryStatus.ACTIVE)
				.build());
		
		categoryRepository.save(Category.builder()
				.name("Ebook")
				.description("Sách trực tuyến, sách online định dạng pdf")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(CategoryStatus.ACTIVE)
				.build());
		
		categoryRepository.save(Category.builder()
				.name("Podcast")
				.description("Podcast")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(CategoryStatus.ACTIVE)
				.build());
		
		categoryRepository.save(Category.builder()
				.name("Gói sách")
				.description("Gói sách")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(CategoryStatus.ACTIVE)
				.build());
		
//		========================================= Load initial data into the Genre database ============================================
		genreRepository.save(Genre.builder()
				.name("Nuôi dạy con")
				.description("Nuôi dạy con")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tài chính doanh nghiệp")
				.description("Tài chính doanh nghiệp")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Thiếu nhi")
				.description("Thiếu nhi")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kinh tế học")
				.description("Kinh tế học")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Bách khoa")
				.description("Bách khoa")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tuổi teen")
				.description("Tuổi teen")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Văn học")
				.description("Văn học")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Thai giáo")
				.description("Thai giáo")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kinh tế thế giới")
				.description("Kinh tế thế giới")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
				
		genreRepository.save(Genre.builder()
				.name("Tư duy làm giàu")
				.description("Tư duy làm giàu")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kỹ năng học tập")
				.description("Kỹ năng học tập")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Cổ tích")
				.description("Cổ tích")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tình yêu")
				.description("Tình yêu")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Triết lý kinh doanh")
				.description("Triết lý kinh doanh")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kỹ năng")
				.description("Kỹ năng")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("PR & Marketing")
				.description("PR & Marketing")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Văn hóa doanh nghiệp")
				.description("Văn hóa doanh nghiệp")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Giới tính")
				.description("Giới tính")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Chuyển đổi số")
				.description("Chuyển đổi số")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Truyện ngắn")
				.description("Truyện ngắn")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tản văn")
				.description("Tản văn")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Sinh học")
				.description("Sinh học")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiểu thuyết")
				.description("Tiểu thuyết")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Công nghệ")
				.description("Công nghệ")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tự truyện & Hồi ký")
				.description("Tự truyện & Hồi ký")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kinh tế vĩ mô")
				.description("Kinh tế vĩ mô")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tài chính cá nhân")
				.description("Tài chính cá nhân")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tư duy chiến lược")
				.description("Tư duy chiến lược")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kỹ năng đàm phán")
				.description("Kỹ năng đàm phán")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Gia đình")
				.description("Gia đình")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Doanh nhân")
				.description("Doanh nhân")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("R&D")
				.description("R&D")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Công nghệ thông tin")
				.description("Công nghệ thông tin")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("AI")
				.description("AI")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiểu thuyết Lịch sử")
				.description("Tiểu thuyết Lịch sử")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiểu thuyết lãng mạn")
				.description("Tiểu thuyết lãng mạn")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiểu thuyết kinh dị")
				.description("Tiểu thuyết kinh dị")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiểu thuyết khoa học viễn tưởng")
				.description("Tiểu thuyết khoa học viễn tưởng")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiểu thuyết giả tưởng")
				.description("Tiểu thuyết giả tưởng")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Truyện dài")
				.description("Truyện dài")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tùy bút")
				.description("Tùy bút")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Bút ký")
				.description("Bút ký")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Phóng sự")
				.description("Phóng sự")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Du ký")
				.description("Du ký")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tự truyện")
				.description("Tự truyện")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Hồi ký")
				.description("Hồi ký")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tài chính")
				.description("Tài chính")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Sức khỏe & Dinh dưỡng")
				.description("Sức khỏe & Dinh dưỡng")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Danh nhân")
				.description("Danh nhân")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Lịch sử tài chính")
				.description("Lịch sử tài chính")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tư duy sáng tạo")
				.description("Tư duy sáng tạo")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Trí tuệ cảm xúc")
				.description("Trí tuệ cảm xúc")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tình dục")
				.description("Tình dục")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Chiến lược kinh doanh")
				.description("Chiến lược kinh doanh")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Customer service")
				.description("Customer service")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kinh doanh 4.0")
				.description("Kinh doanh 4.0")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Môi trường")
				.description("Môi trường")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Y học")
				.description("Y học")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiểu sử")
				.description("Tiểu sử")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Dinh dưỡng")
				.description("Dinh dưỡng")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Sức khỏe")
				.description("Sức khỏe")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Thể thao")
				.description("Thể thao")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Phòng & chữa bệnh")
				.description("Phòng & chữa bệnh")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kinh doanh")
				.description("Kinh doanh")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Lối sống")
				.description("Lối sống")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tâm linh - Tôn giáo")
				.description("Tâm linh - Tôn giáo")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Người nổi tiếng")
				.description("Người nổi tiếng")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Đầu tư")
				.description("Đầu tư")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Nghệ thuật sống")
				.description("Nghệ thuật sống")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kỹ năng Làm việc")
				.description("Kỹ năng Làm việc")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Khởi nghiệp")
				.description("Khởi nghiệp")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Quản trị")
				.description("Quản trị")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Branding")
				.description("Branding")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Blockchain")
				.description("Blockchain")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Thiên văn - Vũ trụ")
				.description("Thiên văn - Vũ trụ")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Lối sống tối giản")
				.description("Lối sống tối giản")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Lối sống Bắc Âu")
				.description("Lối sống Bắc Âu")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Lối sống Phương Đông")
				.description("Lối sống Phương Đông")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Sống tỉnh thức")
				.description("Sống tỉnh thức")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tư duy")
				.description("Tư duy")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kinh tế vi mô")
				.description("Kinh tế vi mô")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Lịch sử kinh tế")
				.description("Lịch sử kinh tế")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tư duy thành công")
				.description("Tư duy thành công")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Kỹ năng giao tiếp")
				.description("Kỹ năng giao tiếp")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tình bạn")
				.description("Tình bạn")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Câu chuyện kinh doanh")
				.description("Câu chuyện kinh doanh")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Sales")
				.description("Sales")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Lãnh đạo")
				.description("Lãnh đạo")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tiền số")
				.description("Tiền số")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Vật lý")
				.description("Vật lý")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tinh thần")
				.description("Tinh thần")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tâm lý học hành vi")
				.description("Tâm lý học hành vi")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tâm lý học")
				.description("Tâm lý học")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Tâm lý học nhận thức")
				.description("Tâm lý học nhận thức")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());
		
		genreRepository.save(Genre.builder()
				.name("Huyền bí - Tiên tri")
				.description("Huyền bí - Tiên tri")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(GenreStatus.ACTIVE)
				.build());	
		
//		========================================= Load initial data into the Publisher database ============================================
		
		publisherRepository.save(Publisher.builder()
				.name("Huy Hoàng")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(PublisherStatus.ACTIVE)
				.build());
		
		publisherRepository.save(Publisher.builder()
				.name("NHÀ XUẤT BẢN TRI THỨC")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(PublisherStatus.ACTIVE)
				.build());
		
		publisherRepository.save(Publisher.builder()
				.name("Nhà xuất bản Trẻ")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(PublisherStatus.ACTIVE)
				.build());
		
		publisherRepository.save(Publisher.builder()
				.name("NHÀ XUẤT BẢN THÔNG TIN VÀ TRUYỀN THÔNG")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(PublisherStatus.ACTIVE)
				.build());
		
		publisherRepository.save(Publisher.builder()
				.name("Nguyễn Phương Chi")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(PublisherStatus.ACTIVE)
				.build());
		
		
//		========================================= Load initial data into the Book database ============================================
		
//		--------------------- Book 1 -----------------
		searchList.clear();
		searchList.add("Sức khỏe & Dinh dưỡng");
		searchList.add("Y học");
		
		bookRepository.save(Book.builder()
				.title("Khúc Tráng Ca Về Những Vết Thương")
				.author("Haider Warraich")
				.description("Trong Khúc Tráng Ca Về Những Vết Thương, bác sĩ Haider Warraich mang đến những nghiên cứu đầy táo bạo về bản chất của cơn đau, đó không chỉ là một cảm giác thể chất đơn giản mà còn liên quan mật thiết đến sức khỏe tinh thần, nền văn hóa, và các mối quan hệ trong cộng đồng.\r\n"
						+ "\r\n"
						+ "Cuốn sách cho người đọc hiểu biết toàn diện về cơ thể con người, về ảnh hưởng của những cơn đau đến cuộc sống của chúng ta, cách ta nhìn nhận ý nghĩa của nó và chế ngự nó trong suốt chiều dài lịch sử. Từ đó, chúng ta nắm bắt được bản chất của nỗi đau, cũng như mối liên kết chặt chẽ giữa cơ thể với tâm trí, và nhận ra rằng không thể lờ đi vai trò của chủng tộc, giới tính và quyền lực đối với sự tồn tại của con người.\r\n"
						+ "\r\n"
						+ "Mạnh mẽ và sâu sắc, Khúc Tráng Ca Về Những Vết Thương vừa là bản cáo trạng về một hệ thống y tế đã bị phá vỡ, vừa là mong muốn tha thiết cho một thế giới mà nỗi đau của con người được quan tâm và thấu hiểu toàn diện hơn.")
				.publisher(publisherRepository.findByName("Huy Hoàng"))
				.ISBN("978-604-1-24659-2")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 2 -----------------
		searchList.clear();
		searchList.add("Văn học");
		searchList.add("Tiểu thuyết Lịch sử");
		
		bookRepository.save(Book.builder()
				.title("Hai Số Phận")
				.author("Jeffrey Archer")
				.description("Jeffrey Archer viết cuốn tiểu thuyết này dựa theo câu chuyện của anh em Cain và Abel trong Kinh Thánh Cựu Ước. Truyện kể về William Kane, con một triệu phú nổi tiếng trên đất Mỹ, lớn lên trong nhung lụa, người kia là Abel Rosnovski, đứa trẻ không rõ xuất thân, được gia đình người bẫy thú nhặt về nuôi. Một người được ấn định để trở thành chủ ngân hàng khi lớn lên, người kia nhập cư vào Mỹ cùng đoàn người nghèo khổ. \r\n"
						+ "\r\n"
						+ "Sinh ra cùng một ngày gần đầu thế kỷ ở hai phía đối diện nhau trên quả địa cầu, hai người đàn ông gặp nhau bởi số phận và hành trình tìm kiếm giấc mơ. Họ đầy tham vọng, quyền lực, tàn nhẫn - mắc kẹt trong một cuộc đấu tranh không ngừng nghỉ trên hành trình xây dựng một đế chế, với động lực là lòng căm thù tột độ trong họ. Hơn 60 năm và ba thế hệ sau, trải qua chiến tranh, hôn nhân, vận may và thảm họa, Kane và Abel chiến đấu để giành lấy thành công và chiến thắng mà chỉ một người có thể có được.")
				.publisher(publisherRepository.findByName("Huy Hoàng"))
				.ISBN("978-604-1-24659-3")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 3 -----------------
		searchList.clear();
		searchList.add("Kỹ năng");
		searchList.add("Kỹ năng Làm việc");
		
		bookRepository.save(Book.builder()
				.title("Chấm Dứt Thói Trì Hoãn")
				.author("Petr Ludwig & Adela Schicker")
				.description("Dựa trên hơn 100 nghiên cứu khoa học về cơ chế hoạt động của bộ não, cuốn sách tạo ra một \"chương trình\" giúp bạn giải quyết triệt để thói trì hoãn. Tác giả Petr Ludwig cho thấy rằng việc chấm dứt sự trì hoãn là điều cần thiết để phát triển ý thức về mục đích và có một cuộc sống hạnh phúc, trọn vẹn hơn. \r\n"
						+ "\r\n"
						+ "Bạn sẽ học được:\r\n"
						+ "- Vì sao chúng ta trì hoãn,\r\n"
						+ "- Làm thế nào để động viên bản thân tận hưởng công việc, bớt căng thẳng và tập trung hơn\r\n"
						+ "- Làm thế nào để tránh trở thành người nghiện mục tiêu - một người đạt nhiều thành tích cao nhưng không bao giờ thỏa mãn\r\n"
						+ "- Cách tổ chức cuộc sống hàng ngày và theo đuổi ước mơ\r\n"
						+ "- Cách tạo những thói quen tích cực mới và chấm dứt những thói quen xấu\r\n"
						+ "- Cách đối phó với tình trạng bế tắc trước những quyết định lớn\r\n"
						+ "\r\n"
						+ "THE END OF PROCRASTINATION\r\n"
						+ "Copyright © 2013 by Petr Ludwig\r\n"
						+ "arranged with: New Leaf Literary & Media, Inc. 110 West 40th Street, Suite 2201, New York, NY 10018, USA\r\n"
						+ "through Bridge Communications Co., Ltd.")
				.publisher(publisherRepository.findByName("NHÀ XUẤT BẢN TRI THỨC"))
				.ISBN("978-604-484-131-1")
				.publicationDecisionNumber("19/QĐLK-XBSĐT-NXBTrT ngày 28 tháng 02 năm 2024")
				.publicationRegistConfirmNum("552-2024/CXBIPH/1-14/TrT")
				.depositCopy("Tờ khai lưu chiểu số 19/LC-NXBTrT ngày 29 tháng 02 năm 2024")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 4 -----------------
		searchList.clear();
		searchList.add("Tâm lý học");
		searchList.add("Tâm lý học hành vi");
		
		bookRepository.save(Book.builder()
				.title("Nghệ Thuật Quyến Rũ")
				.author("Robert Greene")
				.description("Từ tác giả của cuốn sách bán chạy hàng triệu bản 48 Nguyên Tắc Chủ Chốt Của Quyền Lực và Những Quy Luật Của Bản Chất Con Người, cuốn sách này là một cẩm nang hấp dẫn về sự quyến rũ - hình thức quyền lực tinh tế và hiệu quả nhất. Sự quyến rũ thực ra không chỉ liên quan đến tình dục. Định nghĩa chính xác của quyến rũ là: thao túng điểm yếu lớn nhất của con người - ham muốn niềm vui và sự sảng khoái.\r\n"
						+ " \r\n"
						+ "Khi được nâng lên tầm nghệ thuật, sự quyến rũ, một dạng quyền lực gián tiếp và tinh vi, đã lật đổ các đế chế, giành chiến thắng trong các cuộc bầu cử và nô lệ hóa cả những bộ óc vĩ đại. Tất cả chúng ta đều có sức hấp dẫn, lôi cuốn người khác nhưng không phải ai cũng ý thức được khả năng tiềm tàng này. Cuốn sách này sẽ giúp bạn khám phá và phát huy những lợi điểm vốn có để tạo ảnh hưởng đối với người khác thông qua kiến thức về con người từ các nhà tư tưởng lớn như Freud, Diderot, Nietzsche và Einstein, đồng thời cung cấp một số phương pháp và kỹ năng cụ thể. Tác giả cũng giới thiệu và phân tích minh họa những mẫu người quyến rũ và không quyến rũ điển hình trong lịch sử cũng như trong các tác phẩm văn học để bạn có thể đối chứng và học hỏi thêm từ họ. ")
				.publisher(publisherRepository.findByName("Nhà xuất bản Trẻ"))
				.ISBN("978-604-1-24659-1")
				.publicationDecisionNumber("12/QĐ-NXBT ngày 01 tháng 03 năm 2024")
				.publicationRegistConfirmNum("174-2024/CXBIPH/1-18/Tre")
				.depositCopy("Tờ khai lưu chiểu số 15/LC-NXBT ngày 22 tháng 03 năm 2024")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 5 -----------------
		searchList.clear();
		searchList.add("Sức khỏe & Dinh dưỡng");
		searchList.add("Phòng & chữa bệnh");
		
		bookRepository.save(Book.builder()
				.title("Cơ Thể Tự Chữa Lành - Tập 7")
				.author("Anthony William")
				.description("Cuốn sách thứ 7 của loạt sách nổi tiếng đi sâu vào các vấn đề liên quan đến não bộ - từ các loại virus, vi khuẩn gây hại cho não, tới kim loại nặng độc hại, dược phẩm lưu cữu, các chất gây ô nhiễm, thậm chí cả phóng xạ/bức xạ hiện rất phổ biến trong cuộc sống hiện đại. Không chỉ thế, tác giả còn căn cứ vào các vấn đề nêu trên để lý giải nhiều vấn đề, triệu chứng và bệnh trạng tâm thần hiện đang khiến nhiều y bác sĩ bối rối và hàng ngàn người bệnh khốn khổ.")
				.publisher(publisherRepository.findByName("Huy Hoàng"))
				.ISBN("978-674-1-24859-9")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 6 -----------------
		searchList.clear();
		searchList.add("Tư duy thành công");
		searchList.add("Tư duy");
		
		bookRepository.save(Book.builder()
				.title("Không Có Đỉnh Quá Cao - Từ Làng Quê Bước Ra Chinh Phục Thế Giới")
				.author("Phan Văn Trường")
				.description("Không Có Đỉnh Quá Cao là câu chuyện đi tìm thành công và hạnh phúc của 24 bạn trẻ, và những chia sẻ quý giá của Giáo sư Phan Văn Trường được đúc kết từ chính sự nghiệp lớn lao của ông.\r\n"
						+ "\r\n"
						+ "“Sách này sẽ mang tới cho các bạn những bằng chứng thuyết phục về việc không có đỉnh nào là quá cao, nếu chúng ta thực sự cố gắng. \r\n"
						+ "Đó chính là câu chuyện thực của hơn hai mươi bạn trẻ đã từ những vùng sâu vùng xa đi ra thế giới. Họ đã từng đi 'chân đất' khi còn nhỏ, họ đã từng lội sông để đến trường, có người đã phải tha phương cầu thực nơi đất khách. Cuối cùng, họ đã sớm tìm ra hạnh phúc, tìm ra thành công và quan trọng hơn, tìm ra chính mình. \r\n"
						+ "Các bạn trẻ hãy tự tin khi thấy mình khác mọi người, mọi người khác mình, vì chính sự khác biệt mới tạo nên giá trị thực.\r\n"
						+ "Hãy cứ chăm chỉ, đạo đức và tươi tắn thì vận may chẳng bao giờ vắng!\r\n"
						+ "Hãy tự tin mà tiến bước, vì chính sự tự tin cùng với nội lực và trí tuệ sẽ cho mình sức mạnh mà không chướng ngại nào có thể cản.”")
				.publisher(publisherRepository.findByName("Nhà xuất bản Trẻ"))
				.ISBN("978-604-1-25676-7")
				.publicationDecisionNumber("13/QĐ-NXBT ngày 22 tháng 04 năm 2024")
				.publicationDecisionNumber("1286-2024/CXBIPH/1-74/Tre")
				.depositCopy("Tờ khai lưu chiểu số: 16/LC-NXBT ngày 08 tháng 05 năm 2024")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 7 -----------------
		searchList.clear();
		searchList.add("Kỹ năng");
		searchList.add("Kỹ năng Làm việc");
		
		bookRepository.save(Book.builder()
				.title("Sức Mạnh Của Toàn Tâm Toàn Ý")
				.author("Jim Loehr & Tony Schwartz")
				.description("Sức Mạnh của Toàn Tâm Toàn Ý phát huy bốn nguồn năng lượng chủ yếu  để  bù năng lượng tiêu hao bằng  phương pháp  phục hồi xen kẽ. Cuốn sách tạo ra những thói quen cụ thể để quản trị năng lượng tích cực rèn luyện sự toàn tâm toàn ý.\r\n"
						+ "\r\n"
						+ "Trong cuốn sách New York Times Best Seller này, Jim Loehr và Tony Schwartz đã chứng minh rằng không phải quản trị thời gian mà chính quản trị năng lượng mới là then chốt để duy trì cả hiệu suất cao lẫn sức khỏe, hạnh phúc và cân bằng cuộc sống. Phương pháp rèn luyện Toàn tâm toàn ý của họ dựa trên cơ sở 25 năm kinh nghiệm làm việc với những vận động viên thể thao hàng đầu thế giới. Hai người trong số đó là vận động viên tennis vô địch thế giới Monica Seles và nhà trượt tuyết tốc độ Dan Jansen, từng đạt huy chương vàng Thế vận hội. Phương pháp Rèn luyện Toàn tâm toàn ý đã giúp họ thi đấu hiệu quả hơn dưới áp lực khốc liệt của thể thao đỉnh cao.\r\n"
						+ "")
				.publisher(publisherRepository.findByName("NHÀ XUẤT BẢN TRI THỨC"))
				.ISBN("978-604-340-345-9")
				.publicationDecisionNumber("32/QĐLK-XBSĐT-NXB ngày 23 tháng 5 năm 2022")
				.publicationDecisionNumber("1604-2022/CXBIPH/14-20/TrT")
				.depositCopy("Tờ khai lưu chiểu số 150/LCSĐT-NXBTrT ngày 26 tháng 5 năm 2022")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		

//		--------------------- Book 8 -----------------
		searchList.clear();
		searchList.add("Nuôi dạy con");
		
		bookRepository.save(Book.builder()
				.title("Nghe Thổ Dân Kể Chuyện Dạy Con - Săn Bắt, Hái Lượm & Nghệ Thuật Làm Cha Mẹ")
				.author("Michaeleen Doucleff")
				.description("“Nghe Thổ Dân Kể Chuyện Dạy Con có vô vàn ý tưởng thông minh làm tôi lập tức muốn áp dụng cho con mình.” – Pamela Druckerman, trong mục Bình Luận Sách của The New York Times.\r\n"
						+ "\r\n"
						+ "Tác phẩm xóa bỏ những châm ngôn vốn được xem như là kim chỉ nam trong việc nuôi dạy con trẻ của nền văn hóa phương Tây hàng thập kỷ qua.\r\n"
						+ "Liệu khen ngợi có phải cách tốt nhất để khích lệ trẻ nhỏ? Cha mẹ có cần thường xuyên khơi dậy sự hào hứng và chơi đùa cùng con trẻ không? Ngôn từ có phải cách tốt nhất để giao tiếp với các con hay không?\r\n"
						+ "\r\n"
						+ "Theo chân nhà báo Michaeleen Doucleff, chúng ta sẽ được chứng kiến cách nuôi dạy con ấn tượng của các gia đình thuộc ba cộng đồng cổ xưa đáng kính nhất thế giới: Người Maya ở Mexico, người Inuit ở Bắc Cực, và người Hadzabe ở Tanzania. Những đứa trẻ nơi đây lớn lên với sự hiểu biết, tự tin, tự chủ, điềm tĩnh, có ích cho gia đình và xã hội mà không cần cha mẹ la hét, cằn nhằn hay kiểm soát. Hãy mở rộng tầm mắt, tìm kiếm sự khôn ngoan và sáng tạo trong các kỹ thuật xa xưa để giải quyết những tình huống khó xử nhất trên hành trình nuôi dạy con của ta.")
				.publisher(publisherRepository.findByName("Huy Hoàng"))
				.ISBN("978-004-390-355-1")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 9 -----------------
		searchList.clear();
		searchList.add("Tâm lý học");
		searchList.add("Tâm lý học nhận thức");
		
		bookRepository.save(Book.builder()
				.title("Tư Duy Nhanh Và Chậm")
				.author("Daniel Kahneman")
				.description("Tư Duy Nhanh Và Chậm được tạp chí New York Times bình chọn là sách hay nhất năm 2011.\r\n"
						+ "\r\n"
						+ "Chúng ta thường tự cho rằng con người là sinh vật có lý trí mạnh mẽ, khi quyết định hay đánh giá vấn đề luôn kỹ lưỡng và lý tính. Nhưng sự thật là, dù bạn có cẩn trọng tới mức nào, thì trong cuộc sống hàng ngày hay trong vấn đề liên quan đến kinh tế, bạn vẫn có những quyết định dựa trên cảm tính chủ quan của mình. Trong sách nói Tư Duy Nhanh Và Chậm, cuốn sách nổi tiếng tổng hợp tất cả nghiên cứu được tiến hành qua nhiều thập kỷ của nhà tâm lý học từng đoạt giải Nobel Kinh tế Daniel Kahneman, bạn sẽ thấy những sự hợp lý và phi lý trong tư duy của chính bạn. Cuốn sách được đánh giá là “kiệt tác” trong việc thay đổi hành vi của con người. \r\n"
						+ "\r\n"
						+ "Đây là một cuốn sách hàn lâm dành cho tất cả mọi người. ")
				.publisher(publisherRepository.findByName("NHÀ XUẤT BẢN THÔNG TIN VÀ TRUYỀN THÔNG"))
				.ISBN("978-604-80-6050-3")
				.publicationDecisionNumber("14/QĐ - NXB TTTT ngày 20 tháng 01 năm 2022")
				.publicationRegistConfirmNum("13-2022/CXBIPH/18-02/TTTT")
				.depositCopy("Quý I năm 2022")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
//		--------------------- Book 10 -----------------
		searchList.clear();
		searchList.add("Lối sống");
		searchList.add("Lối sống tối giản");
		
		bookRepository.save(Book.builder()
				.title("Một Cuốn Sách Về Chủ Nghĩa Tối Giản")
				.author("Chi Nguyễn - The Present Writer")
				.description("Đúng như tên gọi, đây có thể chỉ là một (trong nhiều) cuốn sách về Chủ nghĩa tối giản bạn đã, đang và sẽ đọc trong cuộc đời. Nhưng điều khiến nó khác biệt, là tất cả đều được viết dưới trải nghiệm cá nhân và thế giới quan rất riêng của Tiến sĩ Chi Nguyễn (The Present Writer), một người làm khoa học viết về cuộc sống.\r\n"
						+ "\r\n"
						+ "Tối giản, theo quan điểm của tác giả, không chỉ đơn thuần là bỏ đi cái quần, cái áo dư thừa hay dọn dẹp nhà cửa sạch sẽ, mà là cả một “cuộc cách mạng” từ bên trong để biến cuộc sống của mình trở nên tích cực, hiệu năng và ý nghĩa hơn. Nói cách khác, cuốn sách khai thác mọi khía cạnh của cuộc sống dưới góc nhìn tối giản toàn diện (holistic minimalism).\r\n"
						+ "\r\n"
						+ "Cuốn sách này không cho bạn một “công thức chuẩn” để đánh giá thế nào là tối giản và thế nào là không tối giản, hay sống như thế nào mới là hạnh phúc, là đáng sống. Cũng sẽ không có điều gì hoàn toàn đúng và hoàn toàn sai áp đặt lên bạn. Thay vào đó, cuốn sách giới thiệu những khái niệm mở, truyền cảm hứng để bạn thay đổi tư duy và tự đưa ra quyết định đâu là lối sống phù hợp nhất với mình.\r\n"
						+ "\r\n"
						+ "Vậy nên, chính xác thì, đây không-chỉ-là một cuốn sách về Chủ nghĩa tối giản. Đây là một cuốn sách về Sự-Thay-Đổi.")
				.publisher(publisherRepository.findByName("Nguyễn Phương Chi"))
				.ISBN("978-614-80-6059-0")
				.averageStar(0)
				.totalRating(0)
				.bookCoverImg("")
				.genres(genreRepository.findAllByStatusAndNameIn(GenreStatus.ACTIVE, searchList))
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy("HUANDM")
				.status(BookStatus.ACTIVE)
				.build());
		
	}

}
