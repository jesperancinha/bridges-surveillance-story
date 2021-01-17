import unittest

from geo_calculator import add_delta_in_km_to_coord, Coord


class TestStringMethods(unittest.TestCase):

    def setUp(self):
        pass

    def test_add_delta_in_km_to_coord(self):
        coord = Coord(53.32055555555556, -1.7297222222222221)

        new_coord = add_delta_in_km_to_coord(coord, 0, 10)

        self.assertEqual(new_coord.lat, 53.32055555555556)
        self.assertEqual(new_coord.lon, -1.579167190360281)


if __name__ == '__main__':
    unittest.main()
