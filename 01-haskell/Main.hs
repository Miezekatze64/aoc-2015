module Main where

import Data.List
import Data.Maybe

trim :: [Char] -> [Char]
trim input = head $ lines input

part1 :: [Char] -> Int
part1 input = up - down
    where
        input' = trim input
        up = length $ filter ( == '(' ) input'
        down = length input' - up

part2 :: [Char] -> Int
part2 input = 1 + fromMaybe (-1) (elemIndex (-1) $ map (\n -> part1 $ take n input' ) [1..len])
    where
        input' = trim input
        len = length input'

main :: IO ()
main = do
    input <- readFile "./input.txt"
    putStrLn $ "Part 1: " ++ show ( part1 input )
    putStrLn $ "Part 2: " ++ show ( part2 input )
