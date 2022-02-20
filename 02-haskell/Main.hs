{-# LANGUAGE OverloadedStrings #-}
module Main where

import qualified Data.Text as T
type Dimension = (Int, Int, Int)

part1 :: T.Text -> Int
part1 inp = sum $ map calc1 list
    where
        list = parse inp

part2 :: T.Text -> Int
part2 input = sum $ map calc2 list
    where
        list = parse input

calc1 :: Dimension -> Int
calc1 (l, w, h) = 2*l*w + 2*w*h + 2*h*l + min (l*w) ( min (w*h) (h*l) )

calc2 :: Dimension -> Int
calc2 (l, w, h) = 2*l + 2*w + 2*h - 2*max l ( max w h ) + l*w*h

parse :: T.Text -> [Dimension]
parse input = map ( toDimension . map ( read . T.unpack ) . T.splitOn "x" ) $ T.words input

toDimension :: [Int] -> Dimension
toDimension [a, b, c] = (a, b, c)
toDimension input = error ("Unexpected input: " ++ show input)

main :: IO ()
main = do
    input <- T.pack <$> readFile "./input.txt"
    let one = part1 input
    putStrLn ( "Part1: " ++ show one )
    let two = part2 input
    putStrLn ( "Part2: " ++ show two )
