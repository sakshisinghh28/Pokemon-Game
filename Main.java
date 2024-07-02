import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Initialize some moves
        Move tackle = new Move("Tackle", "Normal", 40, 100, "Physical", null);
        Move ember = new Move("Ember", "Fire", 40, 100, "Special", "Burn");
        Move waterGun = new Move("Water Gun", "Water", 40, 100, "Special", null);
        Move razorLeaf = new Move("Razor Leaf", "Grass", 55, 95, "Physical", null);
        Move thunderShock = new Move("Thunder Shock", "Electric", 40, 100, "Special", "Paralyze");
        Move rockThrow = new Move("Rock Throw", "Rock", 50, 90, "Physical", null);
        Move quickAttack = new Move("Quick Attack", "Normal", 40, 100, "Physical", null);

        // Initialize some Pokemon
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass", 45, 49, 49, 65, 65, 45, 5);
        bulbasaur.learnMove(tackle);
        bulbasaur.learnMove(razorLeaf);

        Pokemon charmander = new Pokemon("Charmander", "Fire", 39, 52, 43, 60, 50, 65, 5);
        charmander.learnMove(tackle);
        charmander.learnMove(ember);

        Pokemon squirtle = new Pokemon("Squirtle", "Water", 44, 48, 65, 50, 64, 43, 5);
        squirtle.learnMove(tackle);
        squirtle.learnMove(waterGun);

        Pokemon pikachu = new Pokemon("Pikachu", "Electric", 35, 55, 40, 50, 50, 90, 5);
        pikachu.learnMove(thunderShock);
        pikachu.learnMove(quickAttack);

        Pokemon geodude = new Pokemon("Geodude", "Rock", 40, 80, 100, 30, 30, 20, 5);
        geodude.learnMove(tackle);
        geodude.learnMove(rockThrow);

        // Initialize a trainer
        Trainer ash = new Trainer("Ash");
        ash.getTeam().add(bulbasaur);
        ash.getTeam().add(charmander);
        ash.getTeam().add(squirtle);
        ash.getTeam().add(pikachu);
        ash.getTeam().add(geodude);

        // Display trainer's team
        System.out.println(ash.getName() + "'s team:");
        for (Pokemon p : ash.getTeam()) {
            System.out.println(p.getName() + " (Level " + p.getLevel() + ")");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a Pokemon to battle:");
        for (int i = 0; i < ash.getTeam().size(); i++) {
            System.out.println((i + 1) + ". " + ash.getTeam().get(i).getName());
        }
        int choiceIndex = scanner.nextInt() - 1;
        if (choiceIndex >= 0 && choiceIndex < ash.getTeam().size()) {
            Pokemon chosenPokemon = ash.getTeam().get(choiceIndex);
            System.out.println("You chose " + chosenPokemon.getName() + " to battle!");

            // Select opponent
            System.out.println("Choose your Pokemon:");
            List<Pokemon> opponents = new ArrayList<>(ash.getTeam());
            opponents.remove(chosenPokemon);
            for (int i = 0; i < opponents.size(); i++) {
                System.out.println((i + 1) + ". " + opponents.get(i).getName());
            }
            int opponentIndex = scanner.nextInt() - 1;
            if (opponentIndex >= 0 && opponentIndex < opponents.size()) {
                Pokemon opponent = opponents.get(opponentIndex);
                System.out.println("Battle between " + chosenPokemon.getName() + " and " + opponent.getName() + "!");
                battle(chosenPokemon, opponent);
            } else {
                System.out.println("Invalid opponent choice.");
            }
        } else {
            System.out.println("Invalid Pokemon choice.");
        }

        scanner.close();
    }

    public static void battle(Pokemon p1, Pokemon p2) {
        Scanner scanner = new Scanner(System.in);
        while (p1.getHealth() > 0 && p2.getHealth() > 0) {
            System.out.println(p1.getName() + "'s turn!");
            System.out.println("Choose a move:");
            for (int i = 0; i < p1.getMoves().size(); i++) {
                System.out.println((i + 1) + ". " + p1.getMoves().get(i).getName());
            }
            int choice = scanner.nextInt() - 1;
            if (choice >= 0 && choice < p1.getMoves().size()) {
                p1.attack(p2, p1.getMoves().get(choice));
            } else {
                System.out.println("Invalid move choice!");
            }

            if (p2.getHealth() > 0) {
                System.out.println(p2.getName() + "'s turn!");
                Move opponentMove = p2.getMoves().get(0); // Assume the opponent always uses the first move
                System.out.println(p2.getName() + " used " + opponentMove.getName() + "!");
                p2.attack(p1, opponentMove);
            }
        }
        scanner.close();
    }
}

class Move {
    private String name;
    private String type;
    private int power;
    private int accuracy;
    private String category; // Physical, Special, Status
    private String effect;

    // Constructor
    public Move(String name, String type, int power, int accuracy, String category, String effect) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.category = category;
        this.effect = effect;
    }

    // Apply effect
    public void applyEffect(Pokemon target) {
        // Implement effect logic
    }

    // Getters and setters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getPower() { return power; }
    public int getAccuracy() { return accuracy; }
    public String getCategory() { return category; }
    public String getEffect() { return effect; }
}

class Pokemon {
    private String name;
    private String type;
    private int health;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int level;
    private List<Move> moves;

    // Constructor
    public Pokemon(String name, String type, int health, int attack, int defense,
                   int specialAttack, int specialDefense, int speed, int level) {
        this.name = name;
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.level = level;
        this.moves = new ArrayList<>();
    }

    // Attack method
    public void attack(Pokemon opponent, Move move) {
        if (Math.random() * 100 < move.getAccuracy()) {
            int damage = calculateDamage(move, opponent);
            opponent.takeDamage(damage);
            System.out.println(name + " used " + move.getName() + "!");
        } else {
            System.out.println(name + " missed!");
        }
    }

    // Calculate damage
    private int calculateDamage(Move move, Pokemon opponent) {
        int damage = move.getPower();
        if (move.getCategory().equals("Physical")) {
            damage = (damage * attack / opponent.getDefense()) / 10;
        } else if (move.getCategory().equals("Special")) {
            damage = (damage * specialAttack / opponent.getSpecialDefense()) / 10;
        }
        return damage;
    }

    // Take damage
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            System.out.println(name + " fainted!");
        }
    }

    // Learn move
    public void learnMove(Move move) {
        if (moves.size() < 4) {
            moves.add(move);
        } else {
            System.out.println(name + " already knows 4 moves. Forget a move to learn " + move.getName() + ".");
        }
    }

    // Getters and setters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getHealth() { return health; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpecialAttack() { return specialAttack; }
    public int getSpecialDefense() { return specialDefense; }
    public int getSpeed() { return speed; }
    public int getLevel() { return level; }
    public List<Move> getMoves() { return moves; }
}

class Trainer {
    private String name;
    private List<Pokemon> team;

    // Constructor
    public Trainer(String name) {
        this.name = name;
        this.team = new ArrayList<>();
    }

    // Getters and setters
    public String getName() { return name; }
    public List<Pokemon> getTeam() { return team; }
}
