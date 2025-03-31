class BankCard extends CardDecorator {
    public BankCard(UniversalCard card) {
        super(card);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Функция: Банковская карта");
    }
}