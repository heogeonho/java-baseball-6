package baseball;

import static camp.nextstep.edu.missionutils.Randoms.pickNumberInRange;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Application {
    public static void main(String[] args) {

        List<Integer> userArray = new ArrayList<>();        // 유저가 입력한 숫자 상태 배열
        List<Integer> computerArray;                        // 컴퓨터의 숫자 상태 배열

        computerArray = generateComputerNumbers();          // 컴퓨터 랜덤 배열 생성

        int strike = 0;
        int ball = 0;

        System.out.println("숫자 야구 게임을 시작합니다.");

        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            String input = Console.readLine();

            validateInputCount(input);
            validateInputType(input);
            validateInputDuplicates(input);

            // 유저의 숫자 상태 int 배열 생성
            for (int i = 0; i < input.length(); i++) {
                userArray.add(Character.getNumericValue(input.charAt(i)));
            }

            // 볼 스트라이크 갯수 파악
            for (int i = 0; i < 3; i++) {
                if (computerArray.get(i).equals(userArray.get(i))) {
                    strike++;
                } else if (computerArray.contains(userArray.get(i))) {
                    ball++;
                }
            }

            // 조건에 따라 게임 진행
            if (strike == 0 && ball == 0) {
                System.out.println("낫싱");
            } else if (strike == 3) {
                System.out.println("3스트라이크");
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
                computerArray.clear();

                // 종료 단계 입력받기
                String restartValue = Console.readLine();

                // 입력 값에 따라 수행
                if (restartValue.equals("1")) {
                    computerArray = generateComputerNumbers();
                } else if (restartValue.equals("2")) {
                    break;
                } else {
                    throw new IllegalArgumentException("잘못된 입력 형식입니다.");
                }
            } else if (strike == 0) {
                System.out.printf("%d볼%n", ball);
            } else if (ball == 0) {
                System.out.printf("%d스트라이크%n", strike);
            } else {
                System.out.printf("%d볼 %d스트라이크%n", ball, strike);
            }

            // 턴 종료 및 초기화
            userArray.clear();
            strike = 0;
            ball = 0;
        }

        Console.close();
    }

    private static List<Integer> generateComputerNumbers() {
        List<Integer> computerArray = new ArrayList<>();
        while (computerArray.size() < 3) {
            int randomNumber = pickNumberInRange(1, 9);
            if (!computerArray.contains(randomNumber)) {
                computerArray.add(randomNumber);
            }
        }
        return computerArray;
    }

    private static void validateInputCount(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }

    private static void validateInputType(String input) {
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (!Character.isDigit(ch)) {
                throw new IllegalArgumentException("잘못된 입력 형식입니다.");
            }
        }
    }

    private static void validateInputDuplicates(String input) {
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < input.length(); i++) {
            if (!set.add(input.charAt(i))) {
                set.clear();
                throw new IllegalArgumentException("잘못된 입력 형식입니다.");
            }
        }
        set.clear();
    }

}
