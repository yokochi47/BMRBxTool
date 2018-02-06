/*
    BMRBxTool - XML converter for NMR-STAR data
    Copyright 2013-2018 Masashi Yokochi
    
    https://github.com/yokochi47/BMRBxTool

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

public class aromatic_atom_counter {

	public int total_1h = 0;
	public int total_13c = 0;
	public int total_15n = 0;
	public int total_all = 0;

	public int found_1h = 0;
	public int found_13c = 0;
	public int found_15n = 0;
	public int found_all = 0;

	public boolean has_1h = false;
	public boolean has_13c = false;
	public boolean has_15n = false;

	aromatic_atom_counter(boolean has_1h, boolean has_13c, boolean has_15n) {

		this.has_1h = has_1h;
		this.has_13c = has_13c;
		this.has_15n = has_15n;

	}

	public static aromatic_atom_counter set(aromatic_atom[] atoms, boolean has_1h, boolean has_13c, boolean has_15n) {

		aromatic_atom_counter counter = new aromatic_atom_counter(has_1h, has_13c, has_15n);

		if (has_1h) {

			counter.total_1h = aromatic_atom.total(atoms, atom_types.H);
			counter.total_all += counter.total_1h;
			counter.found_1h = aromatic_atom.found(atoms, atom_types.H);
			counter.found_all += counter.found_1h;

		}

		if (has_13c) {

			counter.total_13c = aromatic_atom.total(atoms, atom_types.C);
			counter.total_all += counter.total_13c;
			counter.found_13c = aromatic_atom.found(atoms, atom_types.C);
			counter.found_all += counter.found_13c;

		}

		if (has_15n) {

			counter.total_15n = aromatic_atom.total(atoms, atom_types.N);
			counter.total_all += counter.total_15n;
			counter.found_15n = aromatic_atom.found(atoms, atom_types.N);
			counter.found_all += counter.found_15n;

		}

		return counter;
	}

	public void add(aromatic_atom_counter atom_counter) {

		if (has_1h) {

			total_1h += atom_counter.total_1h;
			total_all += atom_counter.total_1h;
			found_1h += atom_counter.found_1h;
			found_all += atom_counter.found_1h;

		}

		if (has_13c) {

			total_13c += atom_counter.total_13c;
			total_all += atom_counter.total_13c;
			found_13c += atom_counter.found_13c;
			found_all += atom_counter.found_13c;

		}

		if (has_15n) {

			total_15n += atom_counter.total_15n;
			total_all += atom_counter.total_15n;
			found_15n += atom_counter.found_15n;
			found_all += atom_counter.found_15n;

		}

	}

}
