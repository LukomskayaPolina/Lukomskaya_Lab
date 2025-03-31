
public class Main {
    public static void main(String[] args) {
        UniversalCard card = new UniversalCard();
        UniversalCard cardWithPassport = new Passport(card);
        UniversalCard cardWithInsurance = new InsurancePolicy(cardWithPassport);
        UniversalCard fullyFunctionalCard = new BankCard(cardWithInsurance);

        fullyFunctionalCard.displayInfo();
    }
}