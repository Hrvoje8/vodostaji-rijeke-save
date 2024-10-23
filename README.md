# Vodostaji rijeke Save

## Opis
Ovdje se nalaze podatci o promjenama vodostaja rijeke Save na raznim mjestima tijekom različitih vremenskih razdoblja. Podaci uključuju informacije o datumu i vremenu mjerenja, razini vodostaja, temperaturi zraka, vlažnosti, tlaku zraka, smjeru i brzini vjetra, količini oborina te općem stanju vremena.

## Licenca
Ovi podatci su pod [Creative Commons BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/) licencom. To znači da se podaci mogu slobodno koristiti, dijeliti i mijenjati uz uvjet da se zadrži atribucija autoru i da se svi novi podatci dijele pod istom licencom.

## Autor
Hrvoje Dudjak

## Verzija
v1.0

## Datum kreiranja
23.10.2024.

## Ažuriranja
Podatci se ažuriraju po potrebi.

## Zadnje vrijeme uređivanja
13:46 23.10.2024

## Baza
Korištena baza je **PostgreSQL** te je export u CSV i JSON obavljen sa COPY naredbom.

## Izvor podataka
Podatci su dobiveni sa stranica Državnog hidrometeorološkog zavoda
[Podatci o vodostaju](https://meteo.hr/podaci.php?section=podaci_hidro&param=vodostaj)
[Podatci o vremenu](https://meteo.hr/podaci.php?section=podaci_vrijeme&param=hrvatska1_n&sat=11)

## Jezik
Hrvatski

## Atributi skupa podataka
- **datum**: Datum mjerenja (format: YYYY-MM-DD)
- **vrijeme**: Vrijeme mjerenja (format: HH:MM)
- **stanica**: Naziv lokacije gdje je mjerenje obavljeno
- **vodostaj**: Visina vodostaja u centimetrima naspram kote 0
- **temperatura_zraka**: Temperatura zraka u stupnjevima Celzijusa
- **vlaznost**: Vlažnost zraka u postocima
- **tlak_zraka**: Tlak zraka u hPa
- **vjetar**: JSON objekt koji sadrži:
  - **smjer**: Smjer vjetra
  - **brzina**: Brzina vjetra u m/s
- **oborine**: Količina oborina u milimetrima
- **stanje_vremena**: Opis stanja vremena (npr. Vedro, Oblačno, itd.)