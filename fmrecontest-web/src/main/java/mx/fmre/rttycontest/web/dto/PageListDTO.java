package mx.fmre.rttycontest.web.dto;

import java.util.List;

import lombok.Data;
import mx.fmre.rttycontest.persistence.model.Contest;

@Data
public class PageListDTO {
	private List<Integer> pages;
	private List<Contest> listContest;
}
