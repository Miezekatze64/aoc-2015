{-# LANGUAGE OverloadedStrings #-}
module Main where
import qualified Data.Text as T
import Data.List
import Data.Maybe

type Distance = (T.Text, T.Text, Int)

parse1 :: T.Text -> Distance
parse1 str = (split2 !! 0, split2 !! 1, read $ T.unpack $ split1 !! 1)
    where 
        split1 = map T.strip $ T.splitOn "=" str
        split2 = map T.strip $ T.splitOn "to" $ head split1

solve :: [Distance] -> Int
solve ls = minimum $ map (sum . map (\(f, s) -> dist $ findDist ls f s)) $ map ( route) $ permutations $ cities ls

solve2 :: [Distance] -> Int
solve2 ls = maximum $ map (sum . map (\(f, s) -> dist $ findDist ls f s)) $ map ( route) $ permutations $ cities ls

route :: [String] -> [(T.Text, T.Text)]
route xs
    | length xs < 2 = []
    | otherwise = [(T.pack (xs !! 0), T.pack (xs !! 1))] ++ route ( tail $ xs )

cities :: [Distance] -> [String]
cities = cities2 . unzip3

cities2 :: ([T.Text], [T.Text], [Int]) -> [String]
cities2 (first, second, _) = map head $ group $ sort (map T.unpack first ++ map T.unpack second)

parse :: [T.Text] -> [Distance]
parse = map parse1

dist :: Distance -> Int
dist (_, _, d) = d

findDist :: [Distance] -> T.Text -> T.Text -> Distance
findDist list stra strb = fromMaybe ("", "", 0) $ find (\(a, b, _) -> (a == stra && b == strb) || (a == strb && b == stra) ) list

list :: String -> [Distance]
list contents = parse $ map T.pack $ lines contents

main :: IO ()
main = do
    contents <- readFile "./input.txt"
    putStrLn ( "Part 1: " ++ ( show $ solve $ list contents ) )
    putStrLn ( "Part 2: " ++ ( show $ solve2 $ list contents ) )
