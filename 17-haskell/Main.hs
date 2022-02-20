module Main where

import Data.List
import Data.Ord

part1 :: String -> Int
part1 str = length $ filter (==150) $ map sum $ subsequences arr
    where arr = parse str

part2 :: String -> Int
part2 str = length $ filter (\x -> (length $ head list) == length x) list
    where   arr = parse str
            list = sortBy (comparing length) $ filter (\x->sum x==150) $ subsequences arr

parse :: String -> [Int]
parse = map read . lines

main :: IO ()
main = do
    input <- readFile "./input.txt"
    putStrLn ( "Part 1: " ++ ( show $ part1 input ) )
    putStrLn ( "Part 2: " ++ ( show $ part2 input ) )
