package mx.fmre.rttycontest.persistence.enums;

public enum EmailEstatusEnum {
	RECIVED(1), VERIFIED(2), PARSED(3);

	private int id;

	EmailEstatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static EmailEstatusEnum parse(int status) {
		for (EmailEstatusEnum testing : EmailEstatusEnum.values()) {
			if (status == testing.getId())
				return testing;
		}
		return null;
	}
}
