package com.jml.melichallenge.repository.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
@Database(entities = {SearchTerm.class}, version = 1)
public abstract class MeLiDatabase extends RoomDatabase
{
	public abstract SearchTermDao searchTermDao();

	public static MeLiDatabase create(Context context)
	{
		return Room.databaseBuilder(context, MeLiDatabase.class, "meli_database").addCallback(new Callback()
		{
			@Override
			public void onCreate(@NonNull SupportSQLiteDatabase db)
			{
				super.onCreate(db);



				db.beginTransaction();
				List<ContentValues> contentValuesList = getPrePopupatedSearchTerms();

				try
				{
					for(ContentValues contentValues : contentValuesList)
					{
						db.insert("search_terms", SQLiteDatabase.CONFLICT_ABORT, contentValues);
					}
					db.setTransactionSuccessful();
				}
				finally
				{
					db.endTransaction();
				}
			}
		}).build();
	}

	private static List<ContentValues> getPrePopupatedSearchTerms()
	{
		// Prepopulate DB, so you can see some results even the first time you use the app.

		List<ContentValues> contentValuesList = new ArrayList<>();
		String TERM_COLUMN = "term";


		// A
		contentValuesList.add(newContentValue(TERM_COLUMN, "auto"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "audi s3"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "aire acondicionado"));

		// B
		contentValuesList.add(newContentValue(TERM_COLUMN, "bicicleta"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "mercedez benz"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "bmw"));

		// C
		contentValuesList.add(newContentValue(TERM_COLUMN, "camiones usados"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "chevrolet"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "honda civic"));

		// D
		contentValuesList.add(newContentValue(TERM_COLUMN, "departamento en venta"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "fiat duna"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "drone"));

		// E
		contentValuesList.add(newContentValue(TERM_COLUMN, "ford eco sport"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "sillones esquineros"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "escritorio"));

		// F
		contentValuesList.add(newContentValue(TERM_COLUMN, "ford falcon"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "fiat uno"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "ford focus"));

		// G
		contentValuesList.add(newContentValue(TERM_COLUMN, "volkswagen gol"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "pc gamer"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "golf"));

		// H
		contentValuesList.add(newContentValue(TERM_COLUMN, "toyota hilux"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "heladera"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "reloj hombre"));

		// I
		contentValuesList.add(newContentValue(TERM_COLUMN, "iphone 6"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "jeep ika"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "soladora inverter"));

		// J
		contentValuesList.add(newContentValue(TERM_COLUMN, "samsung j7"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "dodge journey"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "j7 pro"));

		// K
		contentValuesList.add(newContentValue(TERM_COLUMN, "ford ka"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "kangoo"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "kayak"));

		// L
		contentValuesList.add(newContentValue(TERM_COLUMN, "celulares liberados"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "lancha"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "lavarropas"));

		// M
		contentValuesList.add(newContentValue(TERM_COLUMN, "motorhome"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "zapatillas mujer"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "mini cooper"));

		// N
		contentValuesList.add(newContentValue(TERM_COLUMN, "notebook"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "nike"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "botines"));

		// Ñ
		contentValuesList.add(newContentValue(TERM_COLUMN, "estufas ñuke"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "llantas ñandú"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "ñoquera"));

		// O
		contentValuesList.add(newContentValue(TERM_COLUMN, "chevrolet onix"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "xbox one"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "ojotas"));

		// P
		contentValuesList.add(newContentValue(TERM_COLUMN, "peugeot 206"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "ps4"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "parrilla"));

		// Q
		contentValuesList.add(newContentValue(TERM_COLUMN, "alquielr quintas"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "audi q5"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "lg q6"));

		// R
		contentValuesList.add(newContentValue(TERM_COLUMN, "renault clio"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "respaldo sommier"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "casa rodante"));

		// S
		contentValuesList.add(newContentValue(TERM_COLUMN, "subastas"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "samsung s8"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "sillones"));

		// T
		contentValuesList.add(newContentValue(TERM_COLUMN, "tablet"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "torino"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "audi tt"));

		// V
		contentValuesList.add(newContentValue(TERM_COLUMN, "vestidos"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "venta"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "vento"));

		// W
		contentValuesList.add(newContentValue(TERM_COLUMN, "jeep wrangler"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "smart watch"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "waflera"));

		// X
		contentValuesList.add(newContentValue(TERM_COLUMN, "iphone x"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "xiaomi"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "bmw x6"));

		// Y
		contentValuesList.add(newContentValue(TERM_COLUMN, "yamaha e6"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "yate"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "blue yeti"));

		// Z
		contentValuesList.add(newContentValue(TERM_COLUMN, "lg zero"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "zenith"));
		contentValuesList.add(newContentValue(TERM_COLUMN, "casco zeus"));

		return contentValuesList;
	}

	private static ContentValues newContentValue(String term_column, String value)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(term_column, value);
		return contentValues;
	}
}