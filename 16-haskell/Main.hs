{-# Language OverloadedStrings #-}
module Main where

import qualified Data.Text as T
import Data.List
import Data.Maybe

type Thing = (T.Text, Int)
type Aunt = [Thing]

(>==) :: Aunt -> T.Text -> Int
(>==) aunt check = fromMaybe (-1) $ snd $ sequenceA $ find (\t -> fst t == check) aunt

check1 :: Aunt -> Bool
check1 aunt = children && cats && samoyeds && pomeranians && akitas && vizslas && goldfish && trees && cars && perfumes
    where 
    children =      (aunt >== "children") == 3      || (aunt >== "children") == -1
    cats =          (aunt >== "cats") == 7          || (aunt >== "cats") == -1
    samoyeds =      (aunt >== "samoyeds") == 2      || (aunt >== "samoyeds") == -1
    pomeranians =   (aunt >== "pomeranians") == 3   || (aunt >== "pomeranians") == -1
    akitas =        (aunt >== "akitas") == 0        || (aunt >== "akitas") == -1
    vizslas =       (aunt >== "vizslas") == 0       || (aunt >== "vizslas") == -1
    goldfish =      (aunt >== "goldfish") == 3      || (aunt >== "goldfish") == -1
    trees =         (aunt >== "trees") == 3         || (aunt >== "trees") == -1
    cars =          (aunt >== "cars") == 2          || (aunt >== "cars") == -1
    perfumes =      (aunt >== "perfumes") == 1      || (aunt >== "perfumes") == -1

check2 :: Aunt -> Bool
check2 aunt = children && cats && samoyeds && pomeranians && akitas && vizslas && goldfish && trees && cars && perfumes
    where 
    children =      (aunt >== "children") == 3      || (aunt >== "children") == -1
    cats =          (aunt >== "cats") > 7           || (aunt >== "cats") == -1
    samoyeds =      (aunt >== "samoyeds") == 2      || (aunt >== "samoyeds") == -1
    pomeranians =   (aunt >== "pomeranians") < 3    || (aunt >== "pomeranians") == -1
    akitas =        (aunt >== "akitas") == 0        || (aunt >== "akitas") == -1
    vizslas =       (aunt >== "vizslas") == 0       || (aunt >== "vizslas") == -1
    goldfish =      (aunt >== "goldfish") < 3       || (aunt >== "goldfish") == -1
    trees =         (aunt >== "trees") > 3          || (aunt >== "trees") == -1
    cars =          (aunt >== "cars") == 2          || (aunt >== "cars") == -1
    perfumes =      (aunt >== "perfumes") == 1      || (aunt >== "perfumes") == -1

parse :: [T.Text] -> [Aunt]
parse arr = things'
    where
    things =  map ( map (map T.strip . T.splitOn ":") . T.splitOn "," . T.concat . tail . tail . T.splitOn " " ) arr
    things' = map (map (\arr -> (arr !! 0, read $ T.unpack $ arr !! 1) :: Thing ) ) things

part1 :: [Aunt] -> Int
part1 aunts = 1 + ( fromMaybe (-1) $ findIndex check1 aunts )

part2 :: [Aunt] -> Int
part2 aunts = 1 + ( fromMaybe (-1) $ findIndex check2 aunts )

main :: IO ()
main = do
    input <- readFile "./input.txt"
    putStrLn ( "Part 1: " ++ ( show $ part1 $ parse $ T.lines $ T.pack input ) )
    putStrLn ( "Part 2: " ++ ( show $ part2 $ parse $ T.lines $ T.pack input ) )
