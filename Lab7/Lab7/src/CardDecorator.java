abstract class CardDecorator extends UniversalCard {
    protected UniversalCard card;

    public CardDecorator(UniversalCard card) {
        this.card = card;
    }

    @Override
    public void displayInfo() {
        card.displayInfo();
    }
}