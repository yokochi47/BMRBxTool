import java.util.Arrays;

public class MyCategory {

	String name = null;
	String[] items = null;
	int[] data_item_pos = null;

	int data_item_len = 0;
	int data_item_hash = 0;

	public MyCategory(String name) {

		this.name = name;

	}

	public int hash(MyDataItem[] data_items) {

		int hash = 0, l = 0;

		for (MyDataItem data_item : data_items)
			hash += data_item.label.split("\\.")[1].hashCode() * (++l);

		return hash;
	}

	public void bipartite(MyDataItem[] data_items) {

		if (items == null)
			return;

		if (data_item_pos == null || data_item_pos.length != items.length)
			data_item_pos = new int[items.length];

		Arrays.fill(data_item_pos, -1);

		data_item_len = data_items.length;
		data_item_hash = hash(data_items);

		for (int i = 0; i < items.length; i++) {

			for (int j = 0; j < data_items.length; j++) {

				if (data_items[j].label.split("\\.")[1].equals(items[i])) {

					data_item_pos[i] = j;

					break;
				}

			}

		}

	}

}
