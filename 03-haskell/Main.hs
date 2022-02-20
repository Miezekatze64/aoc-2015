module Main where

type Point = (Int, Int)

part1 :: String -> Int
part1 input = length $ fst' $ step1 ([(0, 0)], head $ lines input, (0, 0))

part2 :: String -> Int
part2 input = let result = step2 ([(0, 0)], [(0, 0)], head $ lines input, (0, 0), (0, 0), 0)
    in length ( fst'' result >< snd'' result )

(><) :: (Eq a) => [a] -> [a] -> [a]
(><) x y
    | null x && not (null y) = y
    | null y && not (null x) = x
    | null x && null y = []
    | elem (head x) (tail x) || elem (head x) (tail y) = tail x >< y
    | elem (head y) (tail x) || elem (head y) (tail y) = x >< tail y
    | head x == head y = head x : tail x >< tail y
    | otherwise = head x : head y : tail x >< tail y

fst' :: (a, b, c) -> a
fst' (a, b, c) = a

fst'' :: (a, b, c, d, e, f) -> a
fst'' (a, b, c, d, e, f) = a

snd'' :: (a, b, c, d, e, f) -> b
snd'' (a, b, c, d, e, f) = b

step1 :: ([Point], String, Point) -> ([Point], String, Point)
step1 (arr, "", pt) = (arr, "", pt)
step1 (arr, dir:next, pt) = if np `elem` arr then step1 (arr, next, np) else step1 (np:arr, next, np)
    where
        np  | dir == 'v' = (fst pt + 1, snd pt)
            | dir == '^' = (fst pt - 1, snd pt)
            | dir == '>' = (fst pt, snd pt + 1)
            | dir == '<' = (fst pt, snd pt - 1)
            | otherwise = error "Unknown symbol"

step2 ::  ([Point], [Point], String, Point, Point, Int) -> ([Point], [Point], String, Point, Point, Int) 
step2 (arr1, arr2, "", pt1, pt2, who) = (arr1, arr2, "", pt1, pt2, who)
step2 (arr1, arr2, dir:next, pt1, pt2, who) = if np `elem` arr then step2 (arr1, arr2, next, if who == 0 then np else pt1, if who == 1 then np else pt2, 1-who) else step2 (if who == 0 then np:arr1 else arr1, if who == 1 then np:arr2 else arr2, next, if who == 0 then np else pt1, if who == 1 then np else pt2, 1-who)
    where
        arr | who ==  0  = arr1
            | who ==  1  = arr2
        pt  | who ==  0  = pt1
            | who ==  1  = pt2
        np  | dir == 'v' = (fst pt + 1, snd pt)
            | dir == '^' = (fst pt - 1, snd pt)
            | dir == '>' = (fst pt, snd pt + 1)
            | dir == '<' = (fst pt, snd pt - 1)
            | otherwise = error "Unknown symbol"



main :: IO ()
main = do
    input <- readFile "./input.txt"
    let p1 = part1 input
    putStrLn ( "Part 1: " ++ show p1 )
    let p2 = part2 input
    putStrLn ( "Part 2: " ++ show p2 )
    
