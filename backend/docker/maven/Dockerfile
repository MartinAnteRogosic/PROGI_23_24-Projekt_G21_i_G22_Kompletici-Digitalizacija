FROM openjdk:17-alpine AS builder

# Kopiranje izvornog koda u container
COPY . .
RUN chmod +x mvnw


# Pokretanje builda
RUN ./mvnw clean package -DskipTests

# Stvaranje containera u kojem ce se vrtiti aplikacija
FROM openjdk:17-alpine

# instaliranje tesseract
RUN apk --no-cache add tesseract-ocr

RUN mkdir -p /app/src/main/resources/tessdata/
RUN wget -O /app/src/main/resources/tessdata/hrv.traineddata https://github.com/tesseract-ocr/tessdata/raw/main/hrv.traineddata


ENV TESSDATA_PREFIX=/app/src/main/resources/tessdata/

## Ovdje je moguce instalirati alate potrebne za rad aplikacije. Vjerojatno vam nece trebati, no dobro je znati.
## Linux distro koji se koristi je Alpine, stoga se kao package manager koristi apk
#RUN apk install <nesto>

# Kopiranje izvrsnog JAR-a iz build containera u izvrsni container
COPY --from=builder target/*.jar /app.jar

# Izlaganje porta
EXPOSE 8080

# Naredba kojom se pokrece aplikacija
ENTRYPOINT ["java","-jar","/app.jar"]