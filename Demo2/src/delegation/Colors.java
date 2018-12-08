package delegation;

public enum Colors {

	RED, GREEN, BLANK, YELLOW
}
class testEnum{
	Colors color = Colors.RED;
	public void change() {
        switch (color) {
        case RED:
            color = Colors.GREEN;
            break;
        case YELLOW:
            color = Colors.RED;
            break;
        case GREEN:
            color = Colors.YELLOW;
            break;
		default:
			break;
        }
    }
}
