Endringer:
Jeg har for det meste jobbet i Scanner.java filen.

Først fjerner jeg ledende tab-er og teller indenteringen, som
foreslått i kompendiet, og i tillegg tar jeg en trim() på linjen
som leses for å fjerne unødvendig whitespace (etter å ha hentet
indentering og sånt så klart). Så sjekker jeg om linjen er tom
eller er en kommentert linje, i så fall legger jeg til en 
newLineToken og går videre til neste linje. 

Om linjen ikke er tom, lagrer jeg linjenummeret om det ikke 
allerede er lagret. Dette linjenummeret sjekker jeg for å sikre 
at programmet er riktig indentert på første linje. Hvis det 
ikke er første linje, pusher eller popper jeg indenteringen (igjen
som foreslått i kompendiet), før jeg går videre til å faktisk 
lese programmet.


For å lese programmet, har jeg valgt å lese linjen char for
char, men å håndtere hver char som en string, så jeg kan bruke
regex på den (i ettertid vet jeg ikke om dette var nødvendig, men
jeg har for mange obliger å levere inn til å endre på det nå).

STRING:
Hvis char c er noen form av anførselstegn, starter jeg prosessen
for å lage en string. Da fortsetter jeg å lese char for char, helt 
til jeg møter et nytt anførselstegn, og lager et stringtoken. For 
å sjekke at stringen lukkes, sjekker jeg etter å ha loopet gjennom
til slutten av linjen om siste char er et anførselstegn - om den 
ikke er det, eller om loopen aldri break-er, så er stringen
ikke lukket ordentlig.

NAME:
Hvis c er et alfabetisk tegn uten anførselstegn foran, starter jeg
samme prosess som i STRING, men sjekker istedet at hver char jeg 
legger til i navnet er alfanumerisk, eller er en _, og så brytes den
når den finner noe annet (f.eks whitespace). Sjekker til slutt om
den resulterende stringen finnes som et TokenKind - hvis ikke lager
jeg et varname token ut av den.

INT/FLOAT:
Hvis c er et tall, gjør jeg det samme - looper gjennom chars til 
jeg finner noe som ikke matcher. Inkluderer . av ting som er lov
for å kunne lage flyttall. Om jeg finner et ., setter jeg en boolsk
verdi til true og lager et flyttall token, ellers lages et int token.

COMMENT:
Om c er en #, bryt, legg til newLineToken, og gå videre til neste
linje.

ALT ANNET:
Til slutt har jeg alt annet, om c er =, eller +, eller hva som helst 
annet. Da sjekker jeg om c er et TokenKind - hvis ikke gir jeg 
feilmelding. Hvis det er et TokenKind, sjekker jeg neste char også
for å se om de to sammen er et TokenKind (f.eks !=), og lager et
passende Token.


Til slutt avsluttes linjen ved å legge til et newLineToken.

Jeg endret ikke noe spesielt i Token eller TokenKind klassen som jeg
husker, la kun til et array av values i TokenKind for å lett kunne
lete gjennom lista for å se om en verdi er et token. Har også 
lagt til feilmeldinger for alle feil-testprogrammene.
