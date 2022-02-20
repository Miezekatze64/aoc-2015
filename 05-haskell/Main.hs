module Main where

import Data.List
import Data.Ord

part1 :: String -> Int
part1 str = length $ filter id $ map isNice1 $ lines str

part2 :: String -> Int
part2 str = length $ filter id $ map isNice2 $ lines str

isNice1 :: String -> Bool
isNice1 input = (3 <= countElements "aeiou" input) && not ( containsBadString input) && hasTwiceLetter input

isNice2 :: String -> Bool
isNice2 input = pairs input 0 && doubles input 0

containsBadString :: String -> Bool
containsBadString str = any (`isInfixOf` str) ["ab", "cd", "pq", "xy"]

hasTwiceLetter :: String -> Bool
hasTwiceLetter str = 2 <= maximum ( map length $ group str)

countElements :: Eq a => [a] -> [a] -> Int
countElements a1 a2 = length $ filter id $ map (`elem` a1) a2

pairs :: [Char] -> Int -> Bool
pairs [] _ = False
pairs xs i 
    | length str == 0 = False
    | otherwise = (isInfixOf (take 2 str) (drop 2 str)) || (pairs xs (i+1))
    where str = drop i xs

doubles :: [Char] -> Int -> Bool
doubles [] _ = False
doubles xs i
    | i > (length xs - 3) = False
    | otherwise = ( (xs !! i) ) == ( xs !! (i+2) ) || (doubles xs (i+1))

main :: IO ()
main = do
    file <- readFile "./input.txt"
    putStrLn ( "Part 1: " ++ show ( part1 file ))
    putStrLn ( "Part 2: " ++ show ( part2 file ))
