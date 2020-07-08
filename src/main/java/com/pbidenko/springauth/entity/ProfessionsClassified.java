package com.pbidenko.springauth.entity;

public enum ProfessionsClassified {
	
	ANIMATEUR("Animateur/Animatrisse"),
	REALISATEUR("Realisateur/Realisatrisse"),
	ACTEUR("Acteur/Actrisse"),
	ARTISTE("Artiste"), 
	DECORATEUR("Decorateur/Decoratrisse");
	
	private final String label;
	
	private ProfessionsClassified(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	};
		


}
