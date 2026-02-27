# Flight Reservation CLI — Maven lab project

**Small CLI-based flight reservation & seat-allocation engine (Java 17)**
This project was developed as part of an PDS (Design and Software Patterns) lab exercise (refactor). The program input/outputs and user-facing strings are **in Portuguese** (intentionally). The repo is a compact, self-contained Java/Maven application that demonstrates: domain modelling (Flight / Plane / Row / Seat / Reservation), file parsing/validation, a seat-allocation algorithm that prioritizes contiguous seats, and a small command interpreter for interactive or batch operation.

---

## Tech stack

* Java 17
* Maven (build)
* No external runtime dependencies (pure Java)
* CLI I/O (Portuguese messages)

---

## Key features

* Create flights from file or CLI with configurable seat layouts (e.g. `3x2`, `4x6`).
* Parse optional reservation requests in the same file and apply them on load.
* Interactive command interpreter supporting create, load, reserve, cancel, show map and help.
* Seat allocation algorithm:

  * prioritizes contiguous seats in rows,
  * reserves remaining seats if contiguous block not available,
  * maintains per-row state (`AVAILABLE`, `SEMI`, `FULL`).
* Reservation management (generate IDs, cancel reservations and free seats).
* Batch mode: run a file with commands (program will execute each line).
* All visible messages and outputs are in **Portuguese**.

---

## Project structure (high level)

```
src/
 └─ main/
    └─ java/
       └─ system/
          ├─ main/           # Main class (system.main.Main)
          ├─ cli/            # Command parsing & CLI commands
          ├─ data/           # Flight & reservation file parsers / processors
          ├─ service/        # PlaneBuilder, ReservationManager (allocation logic)
          ├─ utils/          # Domain models: Flight, Plane, Row, Seat, Reservation, etc.
          └─ validation/     # File validation helpers
pom.xml
```

---

## Input file format (flight files)

A flight file contains the flight header (first line) and optional reservation lines:

* **Header line** — must start with `>` then flight code and one or two seat blocks:

  ```
  >TP123 3x2 10x6
  ```

  * Flight code: two uppercase letters followed by 3 or 4 digits (regex `[A-Z]{2}\d{3,4}`)
  * Seat blocks: `<rows>x<seats-per-row>` — one block = tourist only, two blocks = executive then tourist.

* **Reservation lines** (optional) — one per line after header:

  ```
  T 3
  E 1
  ```

  * `T` = Turist (economy), `E` = Executive
  * number = number of passengers in the request

The parser validates format strictly (see `FlightFileParser` / `ReservationFileParser`).

---

## CLI commands (Portuguese outputs)

* `H` — show help/menu
* `I <filename>` — load a flight from `src/main/resources/FlightFiles/<filename>` (also applies reservations inside file)
* `F <flight_code> [Exec] <Tur>` — create flight manually (Exec optional, Tur mandatory)

  * Example: `F TP123 3x2 12x6` or `F TP123 12x6`
* `M <flight_code>` — show reservation map (prints seat matrix with reservation IDs)
* `R <flight_code> <T/E> <num>` — create reservation (T = Turist, E = Executive)

  * Returns a reservation code: `FLIGHTCODE:reservationID` and the list of seat codes assigned.
* `C <flight_code>:<res_num>` — cancel reservation
* `Q` — quit
* All command responses and error messages are printed in Portuguese (e.g. `Voo não encontrado`, `Não foi possível obter lugares para a reserva`, `Código de voo <id>`).

---

## Build & run

### 1) Build with Maven

From project root:

```bash
mvn clean package
```

### 2) Run using Maven exec

```bash
mvn exec:java -Dexec.mainClass="system.main.Main"
```

### 2c) Batch mode (execute commands file)

If you have a commands file (plain text) put it under `src/main/resources/input-files/` and run:

```bash
mvn exec:java -Dexec.mainClass="system.main.Main" -Dexec.args="src/main/resources/input-files/test.txt"
```

The program will execute each line of the `test.txt` file as if typed in the CLI.

---

## Example usage (interactive)

```
Escolha uma opção: (H para ajuda)
H
===== MENU DE OPÇÕES =====
H                       : Exibe este menu de ajuda
I <filename.txt>        : Lê um ficheiro de voo
M <flight_code>         : Mostra o mapa de reservas de um voo
F <flight_code> [Ex] <Tur> : Acrescenta um novo voo (Exec opcional, Tur obrigatória)
R <flight_code> <T/E> <num_lugares> : Acrescenta uma nova reserva
C <flight_code>:<res_num> : Cancela uma reserva
Q                       : Termina o programa
==========================
```

Reserve example:

```
> R TP123 T 3
TP123:1 = 1A | 1B | 1C
```

Cancel example:

```
> C TP123:1
Cancelar reserva: TP123:1
```

Errors (Portuguese) will be shown when file format, flight code or reservation numbers are invalid.

---

## Notes, assumptions & limitations

* The CLI and file outputs are intentionally **Portuguese** to match the lab requirements.
* The project does not persist data across runs — flights and reservations are in-memory only.
* Flight files must be placed under `src/main/resources/FlightFiles/` to be loaded by the `I` command.
* The seat-allocation algorithm aims for contiguous seats and falls back to reserve remaining seats in other rows if necessary; it is deterministic and simple (suitable for lab/demo).
* Input validation is strict: malformed flights or reservation lines are rejected by the parser.

---

## Testing / validation

* Manual testing via CLI is the primary method for this lab project.
* To test batch behavior, create a commands file in `src/main/resources/input-files/` and run the app with that filename as first argument.

---