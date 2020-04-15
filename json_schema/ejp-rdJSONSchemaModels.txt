# For the Catalog Entity:

{
    "$schema": "http://json-schema.org/draft-07/schema",
    "$id": "http://example.com/example.json",
    "type": "object",
    "title": "The Root Schema",
    "description": "The root schema comprises the entire JSON document.",
    "default": {},
    "additionalProperties": true,
    "required": [
        "catalog"
    ],
    "properties": {
        "catalog": {
            "$id": "#/properties/catalog",
            "type": "object",
            "title": "The Catalog Schema",
            "description": "An explanation about the purpose of this instance.",
            "default": {},
            "examples": [
                {
                    "alternative": "",
                    "sameAs": "",
                    "about": "",
                    "location": "",
                    "description": "",
                    "name": "",
                    "homepage": "",
                    "identifier": ""
                }
            ],
            "additionalProperties": true,
            "required": [
                "identifier",
                "alternative",
                "about",
                "name",
                "description",
                "homepage",
                "sameAs",
                "location"
            ],
            "properties": {
                "identifier": {
                    "$id": "#/properties/catalog/properties/identifier",
                    "type": "string",
                    "title": "The Identifier Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                },
                "alternative": {
                    "$id": "#/properties/catalog/properties/alternative",
                    "type": "string",
                    "title": "The Alternative Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                },
                "about": {
                    "$id": "#/properties/catalog/properties/about",
                    "type": "string",
                    "title": "The About Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                },
                "name": {
                    "$id": "#/properties/catalog/properties/name",
                    "type": "string",
                    "title": "The Name Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                },
                "description": {
                    "$id": "#/properties/catalog/properties/description",
                    "type": "string",
                    "title": "The Description Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                },
                "homepage": {
                    "$id": "#/properties/catalog/properties/homepage",
                    "type": "string",
                    "title": "The Homepage Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                },
                "sameAs": {
                    "$id": "#/properties/catalog/properties/sameAs",
                    "type": "string",
                    "title": "The Sameas Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                },
                "location": {
                    "$id": "#/properties/catalog/properties/location",
                    "type": "string",
                    "title": "The Location Schema",
                    "description": "An explanation about the purpose of this instance.",
                    "default": "",
                    "examples": [
                        ""
                    ]
                }
            }
        }
    }
}
