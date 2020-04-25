import math


class Coord:
    def __init__(self, lat, lon):
        self.lat = lat
        self.lon = lon

    def delta(self, lat, lon):
        self.lat += lat
        self.lon += lon


def add_delta_in_km_to_coord(coord, d_lat_km, d_lon_km, planet_radius_km=6371):
    lat = coord.lat
    lon = coord.lon
    d_lat = (180 / math.pi) * (d_lat_km / planet_radius_km)
    d_lon = (180 / math.pi) * (d_lon_km / planet_radius_km) / math.cos(math.radians(lat))

    coord = Coord(lat, lon)
    coord.delta(d_lat, d_lon)
    return coord
