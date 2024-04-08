package hcmus.alumni.news.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsDto {
	private String id;
	private String title;
	private String thumbnail;
	private Integer views;
	private Date publishedAt;
}
