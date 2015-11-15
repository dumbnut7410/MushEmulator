import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class main {
	public static void main(String[] args) {
		String[] memory = main.readCoe(args[0]);
		int[] registerFile = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5040,
				0 };
		int compareValue = 12;
		int insCount = 0;

		for (int i = 0; i < memory.length;) {
			insCount++;
			String instr = memory[i];
			int one = Integer.parseInt(memory[i].charAt(1) + "", 16);
			int two = Integer.parseInt(memory[i].charAt(2) + "", 16);
			int three = Integer.parseInt(memory[i].charAt(3) + "", 16);

			switch (Integer.parseInt(memory[i].charAt(0) + "", 16)) {
			case 2: // add
				registerFile[one] = registerFile[two] + registerFile[three];
				break;

			case 3: // jump
				i = registerFile[three];
				continue;

			case 4: // beq
				if (compareValue == registerFile[three]) {
					i = registerFile[two];
					continue;
				}
				break;

			case 5:// sll
				registerFile[one] = registerFile[two] << registerFile[three];
				break;

			case 7: // li
				String s = memory[i].charAt(1) + "" + memory[i].charAt(2) + ""
						+ memory[i].charAt(3);
				registerFile[0] = Integer.parseInt(s, 16);
				break;

			case 11:// sub
				registerFile[one] = registerFile[two] - registerFile[three];
				if (registerFile[one] == 0) {
					compareValue = 0;
				} else if (registerFile[one] > 0) {
					compareValue = 1;
				} else if (registerFile[one] < 0) {
					compareValue = 3;
				}
				break;

			case 15: // mov
				registerFile[one] = registerFile[two];
				break;

			}
			i++;
		}
		for (int i : registerFile) {
			System.out.println(i);
		}
		System.out.println("instruction count: " + insCount);
	}

	public static String[] readCoe(String fileName) {
		String code = "";

		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(fileName));

			reader.readLine();
			reader.readLine();

			for (String line; (line = reader.readLine()) != null;) {
				if (line.contains(";"))
					break;
				code += line.substring(0, 4) + "\n";
			}

			reader.close();

		} catch (final IOException e) {
			if (e instanceof FileNotFoundException) {
				System.err.println("File " + fileName + " not found");
			}
			System.err.println("something wrong");
			return null;
		}

		return code.split("\n");
	}
}
