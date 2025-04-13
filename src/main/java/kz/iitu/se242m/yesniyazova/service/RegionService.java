package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.Region;

import java.util.List;

public interface RegionService {
    List<Region> getAllRegions();
    Region saveRegion(Region region);
}
