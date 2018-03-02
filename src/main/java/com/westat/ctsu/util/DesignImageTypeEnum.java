package com.westat.ctsu.util;

import net.rcarz.jiraclient.Attachment;

public enum DesignImageTypeEnum {
	ACTIVITY("-activity"), SEQUENCE("-sequence"), CLASS("-class"), STATE("-state");

	private String value;
	private static final String MIME_TYPE = "image/";

	private DesignImageTypeEnum(String value) {
		this.value = value;
	}

	public boolean isMatched(String fileName) {
		return fileName.contains(this.value);
	}

	public static boolean isImage(Attachment attachment) {
		boolean isImage = false;
		if (attachment.getMimeType().contains(MIME_TYPE)) {
			DesignImageTypeEnum[] imageTypeEnums = values();
			for (DesignImageTypeEnum imageTypeEnum : imageTypeEnums) {
				if (imageTypeEnum.isMatched(attachment.getFileName())) {
					isImage = true;
					break;
				}
			}
		} else {
			System.out.println("Not a valid image attachment:: and attachment "
					+ "file Name: should be [*-sequence.*, *-activity.*, *-class.*]:::: " + attachment.getFileName());
		}
		return isImage;
	}

}
