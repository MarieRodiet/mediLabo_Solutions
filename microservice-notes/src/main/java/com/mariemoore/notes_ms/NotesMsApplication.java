package com.mariemoore.notes_ms;

import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.service.PatientNoteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class NotesMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesMsApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PatientNoteService noteService ){
		return args -> {
			noteService.createNote(new Note("2", "1", "TestBorderline",
					"Le patient déclare qu'il ressent beaucoup de stress au travail "
							+ "Il se plaint également que son audition est anormale dernièrement"));

			noteService.createNote(new Note("3", "1", "TestInDanger",
					"Le patient déclare qu'il fume depuis peu"));

			noteService.createNote(new Note("4", "1", "TestEarlyOnset",
					"Le patient déclare qu'il lui est devenu difficile de monter les escaliers "
							+ "Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés "
							+ "Réaction aux médicaments"));

			noteService.createNote(new Note("5", "1", "TestEarlyOnset",
					"Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"));

			noteService.createNote(new Note("6", "1", "TestEarlyOnset",
					"Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"));

			noteService.createNote(new Note("7", "1", "TestEarlyOnset",
					"Taille, Poids, Cholestérol, Vertige et Réaction"));
		};
	}

}
