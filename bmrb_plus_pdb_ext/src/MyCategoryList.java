import java.util.ArrayList;
import java.util.List;

public class MyCategoryList {

	List<MyCategory> list = null;

	public MyCategoryList() {

		list = new ArrayList<MyCategory>();

	}

	public boolean contains(String name) {

		for (MyCategory item : list) {

			if (item.name.equals(name))
				return true;

		}

		return false;
	}

	public void add(MyCategory item) {

		if (list.contains(item))
			return;

		list.add(item);

	}

	public MyCategory get(String name) {

		for (MyCategory item : list) {

			if (item.name.equals(name))
				return item;

		}

		return null;

	}

}
