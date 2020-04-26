import math
import random


class Coord:
    def __init__(self, lat, lon):
        self.lat = lat
        self.lon = lon

    def delta(self, lat, lon):
        self.lat += lat
        self.lon += lon

    def __repr__(self):
        return "lat: " + str(self.lat) + " lon: " + str(self.lon)


def add_delta_in_km_to_coord(coord, d_lat_km, d_lon_km, planet_radius_km=6371):
    lat = coord.lat
    lon = coord.lon
    d_lat = (180 / math.pi) * (d_lat_km / float(planet_radius_km))
    d_lon = (180 / math.pi) * (d_lon_km / float(planet_radius_km)) / math.cos(math.radians(lat))

    return Coord(lat + d_lat, lon + d_lon)


def create_west_random_point(dest, radius):
    degree = random.randint(-90, 90)
    d_lat_km = math.cos(math.radians(degree)) * radius
    d_lon_km = math.sin(math.radians(degree)) * radius
    origin = Coord(dest.lat, dest.lon)
    origin = add_delta_in_km_to_coord(origin, -d_lat_km, d_lon_km)
    return origin


def create_east_random_point(dest, radius):
    degree = random.randint(-90, 90)
    d_lat_km = math.cos(math.radians(degree)) * radius
    d_lon_km = math.sin(math.radians(degree)) * radius
    origin = Coord(dest.lat, dest.lon)
    origin = add_delta_in_km_to_coord(origin, +d_lat_km, d_lon_km)
    return origin
